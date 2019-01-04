package com.paladin.hf.controller.ordinary;

import io.netty.handler.codec.base64.Base64Encoder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.UserSession;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.model.ordinary.Prizepunish;
import com.paladin.hf.service.ordinary.PrizepunishService;


/**
 * 平时考评个人-奖惩事件
 * 
 * @author 黄伟华
 * @version 2018年1月15日 下午4:31:17
 */
@Controller
@RequestMapping("/console/peacet")
public class PrizepunishController extends ControllerSupport {
	@Autowired
	private PrizepunishService prizepunishService;
	@Autowired
	private ImageService imageService;

	@Value("${web.upload.path}")
	public String webUploadPath;

	@Value("${web.upload.path.file}")
	public String webUploadPathFile;

	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(Model model, Prizepunish prizepunish, @RequestParam(required = false) String state, @RequestParam(required = false) String isNull,
			@RequestParam(required = false) String isunit, @RequestParam(required = false) String isAgency) {

		if (HfUserSession.getCurrentUserSession().isAdminRoleLevel()) {
			return "no_business";
		}
		model.addAttribute("isunit", isunit);
		model.addAttribute("orgUserId", prizepunish.getOrgUserId());
		model.addAttribute("state", prizepunish.getState());
		model.addAttribute("stateUnit", prizepunish.getStateUnit());
		model.addAttribute("isNull", isNull);
		if (("1").equals(isNull)) {
			if(isAgency == null) {
				model.addAttribute("backurl", "/console/prize/index?cached=1");
			} else {
				model.addAttribute("backurl", "/console/peacedunit/index?cached=1");
			}
		}
		return "console/peacepeople/index";
	}

	/**
	 * PC端端奖惩事件列表查询
	 * 
	 * @param prizepunish
	 * @param model
	 * @param a
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public Object list(Prizepunish prizepunish, Model model, String isNull) {
		try {
			ModelMap map = new ModelMap();
			UserSession session = UserSession.getCurrentUserSession();

			if (!("1").equals(isNull)) {
				String userid = session.getUserId();
				prizepunish.setOrgUserId(userid);
			}

			List<Map<String, Object>> Lists = prizepunishService.getPageList(prizepunish);
			map.put("pageInfo", new PageInfo<Map<String, Object>>(Lists));
			map.put("queryParam", prizepunish);
			
			return CommonResponse.getSuccessResponse(prizepunishService.getPageList(prizepunish));
			return ReturnUtil.Success("加载成功", map, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error("加载失败", null, null);
		}
	}

	/**
	 * 科室(机构)为某个人新增奖惩事件页面跳转
	 * 
	 * @param model
	 * @param isunit
	 * @param orgUserId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/peac/input", method = { RequestMethod.GET })
	public String peacedInput(Model model, String isunit, String orgUserId) {
		model.addAttribute("orgUserId", orgUserId);
		model.addAttribute("isunit", isunit);
		model.addAttribute("prize", new Prizepunish());
		model.addAttribute("images", new Images());
		model.addAttribute("formType", FormType.ADD);
		return "console/peacedepartment/peaced_from";
	}

	/**
	 * 科室(机构)为某个人修改奖惩事件页面跳转
	 * 
	 * @param model
	 * @param isunit
	 * @param orgUserId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/peac/edit", method = { RequestMethod.GET })
	public String peacedEdit(@RequestParam(required = true) String id, String isunit, String orgUserId, Model model, HttpServletRequest request) {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/app/";
		Prizepunish prizepunish = prizepunishService.get(id);
		List<Images> images = imageService.imagesId(id);
		String typeName = null;
		if (prizepunish == null)
			prizepunish = new Prizepunish();
		if (images.size() > 0) {
			typeName = images.get(0).getTypeName();
		}
		model.addAttribute("orgUserId", orgUserId);
		model.addAttribute("isunit", isunit);
		model.addAttribute("basePath", basePath);
		model.addAttribute("images", images);
		model.addAttribute("imageType", typeName);
		model.addAttribute("prize", prizepunish);
		model.addAttribute("formType", FormType.EDIT);
		return "console/peacedepartment/peaced_from";
	}

	/**
	 * PC端科室(机构)为某个人新增奖惩事件
	 * 
	 * @param prizepunish
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 * @see [类、类#方法、类#成员]
	 */
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/peac/save", method = { RequestMethod.POST })
	public Object peacSaveorUpdate(@Valid Prizepunish prizepunish, Images image, HttpServletRequest request, @RequestParam("isfile") MultipartFile[] isfiles,
			String filess) throws IllegalStateException, IOException {
		String uuid = UuidUtil.getUUID();
		System.out.println(request.getParameter("isfile"));
		String filePath;
		if (!StringUtils.isEmpty(prizepunish.getId())) {
			filePath = webUploadPath + prizepunish.getId();
		} else {
			filePath = webUploadPath + uuid + "/";
		}
		if (filess == null) {
			UploadImageAanFile.imageAanFile(uuid, prizepunish.getId(), filePath, isfiles);
		}

		prizepunish.setOrgUserId(prizepunish.getOrgUserId());
		if (prizepunish.getOrgUserId() != null && request.getParameter("isunit").equals("0")) {
			if (request.getParameter("submitname").equals("提交")) {
				prizepunish.setOperationState(Prizepunish.OPERATION_STATE_DEPARTMENT_SUBMIT);
				prizepunish.setExamineState("1");
				prizepunish.setExaminePeople(UserSession.getCurrentUserSession().getUserName());
			}
			if (request.getParameter("submitname").equals("暂存")) {
				prizepunish.setOperationState(Prizepunish.OPERATION_STATE_DEPARTMENT_TEMPORARY);
				prizepunish.setExamineState(Prizepunish.OPERATION_STATE_SELF_TEMPORARY);
			}
		}
		if (prizepunish.getOrgUserId() != null && request.getParameter("isunit").equals("1")) {
			if (request.getParameter("submitname").equals("提交")) {
				prizepunish.setOperationState(Prizepunish.OPERATION_STATE_AGENCY_SUBMIT);
				prizepunish.setExamineState("1");
				prizepunish.setExaminePeople(UserSession.getCurrentUserSession().getUserName());
			}
			if (request.getParameter("submitname").equals("暂存")) {
				prizepunish.setOperationState(Prizepunish.OPERATION_STATE_AGENCY_TEMPORARY);
				prizepunish.setExamineState(Prizepunish.OPERATION_STATE_SELF_TEMPORARY);
			}
		}
		if (StringUtils.isEmpty(prizepunish.getId())) {
			prizepunish.setId(uuid);
			return CommonResponse.getResponse(prizepunishService.save(prizepunish));
		} else {
			return CommonResponse.getResponse(prizepunishService.updateSelective(prizepunish));
		}

	}

	/**
	 * PC端奖惩事件新增页面跳转
	 * 
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/add/input", method = { RequestMethod.GET })
	public String addInput(Model model) {
		model.addAttribute("formType", FormType.ADD);
		model.addAttribute("prize", new Prizepunish());
		model.addAttribute("images", new Images());
		return "console/peacepeople/from";
	}

	/**
	 * PC端奖惩事件单条数据查询
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping("/view")
	@CrossOrigin(origins = "*", maxAge = 3600)
	public String view(@RequestParam(required = true) String id, Model model, HttpServletRequest request) {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/app/";
		Prizepunish prizepunish = prizepunishService.prizepId(id);
		List<Images> images = imageService.imagesId(id);
		String typeName = null;
		if (prizepunish == null)
			prizepunish = new Prizepunish();
		if (images.size() > 0) {
			typeName = images.get(0).getTypeName();
		}
		model.addAttribute("basePath", basePath);
		model.addAttribute("prize", prizepunish);
		model.addAttribute("images", images);
		model.addAttribute("imageType", typeName);
		model.addAttribute("formType", FormType.VIEW);
		return "console/peacepeople/from";
	}

	/**
	 * PC端奖惩事件修改页面跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("/edit/input")
	public String editInput(@RequestParam(required = true) String id, Model model, HttpServletRequest request) {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/app/";
		Prizepunish prizepunish = prizepunishService.get(id);
		List<Images> images = imageService.imagesId(id);
		String typeName = null;
		if (prizepunish == null)
			prizepunish = new Prizepunish();
		if (images.size() > 0) {
			typeName = images.get(0).getTypeName();
		}
		model.addAttribute("basePath", basePath);
		model.addAttribute("images", images);
		model.addAttribute("imageType", typeName);
		model.addAttribute("prize", prizepunish);
		model.addAttribute("formType", FormType.EDIT);
		return "console/peacepeople/from";
	}

	/**
	 * PC端奖惩事件新增,修改
	 * 
	 * @param prizepunish
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 * @see [类、类#方法、类#成员]
	 */
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public Object saveorUpdate(@Valid Prizepunish prizepunish, Images image, HttpServletRequest request, @RequestParam("isfile") MultipartFile[] isfiles,
			String filess) throws IllegalStateException, IOException {
		String uuid = UuidUtil.getUUID();
		System.out.println(request.getParameter("isfile"));
		String filePath;
		if (!StringUtils.isEmpty(prizepunish.getId())) {
			filePath = webUploadPath + prizepunish.getId();
		} else {
			filePath = webUploadPath + uuid + "/";
		}
		if (filess == null) {
			UploadImageAanFile.imageAanFile(uuid, prizepunish.getId(), filePath, isfiles);
		}
		if (request.getParameter("submitname").equals("提交")) {
			prizepunish.setOperationState("3");
			prizepunish.setExamineState("0");
		}
		if (request.getParameter("submitname").equals("暂存")) {
			prizepunish.setOperationState("0");
		}
		if (StringUtils.isEmpty(prizepunish.getId())) {
			prizepunish.setId(uuid);
			prizepunish.setOrgUserId(UserSession.getCurrentUserSession().getUserId());
			return CommonResponse.getResponse(prizepunishService.save(prizepunish));
		} else {
			return CommonResponse.getResponse(prizepunishService.updateSelective(prizepunish));
		}

	}

	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/prize/view", method = { RequestMethod.GET })
	public ModelMap findList(Prizepunish prizepunish, Model model) {
		try {
			ModelMap map = new ModelMap();
			List<Map<String, Object>> Lists = prizepunishService.getPageList(prizepunish);
			map.put("pageInfo", new PageInfo<Map<String, Object>>(Lists));
			map.put("queryParam", prizepunish);
			return ReturnUtil.Success("加载成功", map, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error("加载失败", null, null);
		}
	}

	/**
	 * 人员周期考评-奖惩事件查看
	 * 
	 * @param prizepunish
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/prize/view2", method = { RequestMethod.GET })
	public ModelMap findList2(Prizepunish prizepunish, Model model) {
		try {
			ModelMap map = new ModelMap();
			List<Map<String, Object>> Lists = prizepunishService.getPageList2(prizepunish);
			map.put("pageInfo", new PageInfo<Map<String, Object>>(Lists));
			map.put("queryParam", prizepunish);
			return ReturnUtil.Success("加载成功", map, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error("加载失败", null, null);
		}
	}

	// 文件下载
	@RequestMapping(value = "/download", method = { RequestMethod.GET })
	public String downloadFile(HttpServletResponse response, String name, String id, String prizName) throws UnsupportedEncodingException {
		String fileName = name;
		if (fileName != null) {
			String realPath = webUploadPath + id + "/";

			File file = new File(realPath, fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(prizName, "UTF-8"));
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					System.out.println("success");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}
	/*
	 * ==================================APP端=======================================
	 * ==
	 */

	/**
	 * APP端奖惩事件列表查询
	 * 
	 * @param prizepunish
	 * @param model
	 * @param a
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "app/list", method = { RequestMethod.GET })
	public ModelMap appList(Prizepunish prizepunish, Model mode) {
		try {
			ModelMap map = new ModelMap();
			UserSession session = UserSession.getCurrentUserSession();
			prizepunish.setOrgUserId(session.getUserId());
			List<Map<String, Object>> Lists = prizepunishService.appGetPageList(prizepunish);
			map.put("pageInfo", new PageInfo<Map<String, Object>>(Lists));
			map.put("queryParam", prizepunish);
			return ReturnUtil.Success("加载成功", map, null);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnUtil.Error("加载失败", null, null);
		}
	}

	/**
	 * APP端奖惩事件单条数据查询
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@ResponseBody
	@RequestMapping("/appView")
	@CrossOrigin(origins = "*", maxAge = 3600)
	public Object appView(@RequestParam(required = true) String id, Model model, HttpServletRequest request) {
		ModelMap map = new ModelMap();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/app/";
		Prizepunish prizepunish = prizepunishService.get(id);
		List<Images> images = imageService.imagesId(id);
		if (images != null) {
			for (Images images2 : images) {
				String image = webUploadPath + images2.getPrizeId() + "/" + images2.getImgName();
				File file = new File(webUploadPath + images2.getPrizeId());
				if (file.exists()) {
					InputStream inputStream = null;
					byte[] data = null;
					try {
						inputStream = new FileInputStream(image);
						data = new byte[inputStream.available()];
						inputStream.read(data);
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// 加密
					Base64Encoder encoder = new Base64Encoder();
					encoder.encode(data);
					images2.setImgName(encoder.encode(data));
				}

			}

		}

		if (prizepunish == null)
			prizepunish = new Prizepunish();
		map.put("basePath", basePath);
		map.put("prizepunish", prizepunish);
		map.put("images", images);
		return CommonResponse.getSuccessResponse(map);
	}

	/**
	 * APP端奖惩事件新增,修改
	 * 
	 * @param prizepunish
	 * @param request
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "app/save", method = { RequestMethod.POST })
	public Object appSaveorUpdate(@RequestBody Prizepunish prizepunish) {

		String uuid = UuidUtil.getUUID();
		String filePath;
		if (!StringUtils.isEmpty(prizepunish.getId())) {
			filePath = webUploadPath + prizepunish.getId();
		} else {
			filePath = webUploadPath + uuid + "/";
		}

		if (!StringUtils.isEmpty(prizepunish.getId())) {
			uuid = prizepunish.getId();
			File pathFile = new File(filePath);
			File[] file = pathFile.listFiles();
			if (file != null) {
				for (File file2 : file) {
					file2.delete();
				}
				pathFile.delete();
			}
			imageService.deleteImage(prizepunish.getId());
		}

		if (prizepunish.getFileImage() != null) { // 图像数据为空

			Base64Encoder encoder = new Base64Encoder();
			try {
				// Base64解码
				for (int i = 0; i < prizepunish.getFileImage().length; i++) {
					byte[] b = encoder.decode(prizepunish.getFileImage()[i]);
					for (int j = 0; j < b.length; ++j) {
						if (b[j] < 0) {
							b[j] += 256;
						}
					}
					String imgSuffix = ".jpg";
					String imgNameUuid = UuidUtil.getUUID();
					;
					if (!StringUtils.isEmpty(prizepunish.getId())) {
						filePath = webUploadPath + prizepunish.getId();
					} else {
						filePath = webUploadPath + uuid;
					}

					String imgFilePath = filePath + "/" + imgNameUuid + imgSuffix;// 新生成的图片
					File dest = new File(imgFilePath);
					if (!dest.getParentFile().exists()) {
						dest.getParentFile().mkdirs();
					}
					OutputStream out = new FileOutputStream(imgFilePath); // 生成jpeg图片
					out.write(b);
					Images image = new Images();
					image.setTypeName("1");
					image.setImgId(UuidUtil.getUUID());
					image.setPrizeId(uuid);
					image.setImgName(imgNameUuid + imgSuffix);
					image.setImgFileName(imgNameUuid + imgSuffix);
					image.setImgSuffix(imgSuffix);
					imageService.save(image);

					out.flush();
					out.close();
				}
			} catch (Exception e) {
				return false;
			}
		}
		if (prizepunish.getSubmitname().equals("提交")) {
			prizepunish.setOperationState("3");
			prizepunish.setExamineState("0");
		}
		if (prizepunish.getSubmitname().equals("暂存")) {
			prizepunish.setOperationState("0");
		}
		if (StringUtils.isEmpty(prizepunish.getId())) {
			prizepunish.setId(uuid);
			prizepunish.setOrgUserId(UserSession.getCurrentUserSession().getUserId());
			return CommonResponse.getResponse(prizepunishService.save(prizepunish));
		} else {
			return CommonResponse.getResponse(prizepunishService.updateSelective(prizepunish));
		}

	}

	/**
	 * PC端, APP端奖惩事件删除
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	public Object delete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(prizepunishService.removeByPrimaryKey(id));
	}
}
