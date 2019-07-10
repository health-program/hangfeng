package com.paladin.hf.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.excel.DefaultSheet;
import com.paladin.framework.excel.read.DefaultReadColumn;
import com.paladin.framework.excel.read.ExcelReadException;
import com.paladin.framework.excel.read.ExcelReader;
import com.paladin.framework.excel.read.ReadColumn;
import com.paladin.framework.excel.read.ReadProperty;
import com.paladin.framework.excel.write.DefaultWriteRow;
import com.paladin.framework.excel.write.ExcelWriter;
import com.paladin.framework.excel.write.WriteProperty;
import com.paladin.framework.excel.write.WriteRow;
import com.paladin.framework.spring.SpringContainer;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.org.OrgUserService;

/**
 * Excel 导入人员身份证信息代码
 * @author TontoZhou
 * @since 2019年7月10日
 */
//@Component
public class ExcelReaderContainer implements SpringContainer {

	private static final Map<String, String> unitMap = new HashMap<>();

	static {
		unitMap.put("昆山市卫生和计划生育委员会", "8a8181cd2ccf1f2e012ccf1f2e4a0000");
		unitMap.put("昆山市巴城镇社区卫生服务中心", "20110119002016");
		unitMap.put("昆山市妇幼保健所", "20110119002028");
		unitMap.put("昆山市青阳社区卫生服务中心", "20110119002027");
		unitMap.put("昆山市第一人民医院", "8a8181cd2cce150e012cce150e770000");
		unitMap.put("昆山市社区卫生服务管理中心", "");
		unitMap.put("昆山市花桥人民医院", "20110111001263");
		unitMap.put("昆山市第三人民医院", "20110119002018");
		unitMap.put("昆山市疾病预防控制中心", "20110119002026");
		unitMap.put("昆山市中医医院", "20110119002043");
		unitMap.put("昆山市计划生育协会", null);
		unitMap.put("昆山高新区江浦社区卫生服务中心", "20110119002039");
		unitMap.put("昆山市健康促进中心", "3e313489309c4f6eb0c3dcbf62a6c8c8");
		unitMap.put("昆山市锦溪镇社区卫生服务中心", "20110119002025");
		unitMap.put("昆山市千灯镇社区卫生服务中心", "20110119002034");
		unitMap.put("昆山市柏庐社区卫生服务中心", "20170630250129");
		unitMap.put("昆山市周市镇社区卫生服务中心", "20110120002127");
		unitMap.put("昆山市张浦镇社区卫生服务中心", "20110119002040");
		unitMap.put("昆山市急救中心", "20110119002035");
		unitMap.put("昆山市精神卫生中心", "20110119002015");
		unitMap.put("昆山市花桥镇社区卫生服务中心", "20110119002023");
		unitMap.put("昆山市康复医院", "20110108001140");
		unitMap.put("昆山市千灯人民医院", "20110119002033");
		unitMap.put("昆山市锦溪人民医院", "20110119002024");
		unitMap.put("昆山市第四人民医院", "20110119002037");
		unitMap.put("昆山市周庄人民医院（周庄镇社区卫生服务中心）", "20110119002045");
		unitMap.put("昆山市第二人民医院", "20110119002017");
		unitMap.put("昆山市震川社区卫生服务中心", "20170630250131");
		unitMap.put("昆山市陆家镇社区卫生服务中心", "20110119002029");
		unitMap.put("昆山市卫生计生信息中心", null);
		unitMap.put("昆山市周庄人民医院", "20110119002044");
		unitMap.put("昆山市城乡卫生指导中心", null);
		unitMap.put("昆山市亭林社区卫生服务中心", "20170630250130");
		unitMap.put("昆山市卫生监督所", "20110113001681");
		unitMap.put("昆山市红十字会血站", "20110119002030");
		unitMap.put("昆山市第六人民医院", "20110119002019");
		unitMap.put("昆山市淀山湖镇社区卫生服务中心", "20110119002021");
		unitMap.put("昆山经济技术开发区蓬朗社区卫生服务中心", "20110119002032");
		unitMap.put("昆山市淀山湖人民医院", "20110119002020");
	}

	@Autowired
	private OrgUserService userService;
	
	@SuppressWarnings("resource")
	public boolean afterInitialize() {
		
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(new FileInputStream("D:/user.xls"));
		} catch (IOException e1) {
			throw new BusinessException("导入异常");
		}

		List<ReadColumn> columns = DefaultReadColumn.createReadColumn(ImportUser.class);
		ExcelReader<ImportUser> reader = new ExcelReader<>(ImportUser.class, columns, new DefaultSheet(workbook.getSheetAt(0)), 2);
		List<ImportUser> users = new ArrayList<>();

		while (reader.hasNext()) {
			ImportUser excelUser = null;
			try {
				excelUser = reader.readRow();
				String unitName = excelUser.getUnitName();
				if (unitName != null && unitName.length() > 0) {
					users.add(excelUser);
				}
			} catch (ExcelReadException e) {
				e.printStackTrace();
				continue;
			}
		}

		int count = 0;
		for (ImportUser user : users) {
			String unitName = user.getUnitName();
			String unitId = unitMap.get(unitName);
			String name = user.getName();
			String identification = user.getIdentification();

			Condition[] conditions = new Condition[] { new Condition("name", QueryType.EQUAL, name), new Condition("orgAgencyId", QueryType.EQUAL, unitId) };

			List<OrgUser> us = userService.findUserByIdentification(identification);
			boolean has = false;
			if (us != null && us.size() > 0) {
				has = true;
				user.setHasIdInSystem("是");

				boolean b = false;
				for (OrgUser u : us) {
					if (u.getName().equals(name)) {
						b = true;
					}
				}

				if (b) {
					user.setIsSameUser(us.size() > 1 ? "是，但有多个人员" : "是");
				} else {
					user.setIsSameUser("否");
				}
			}

			if (!has) {
				if (unitId == null) {
					List<OrgUser> orgUsers = userService.searchAll(conditions[0]);
					if (orgUsers != null && orgUsers.size() > 0) {
						user.setNote("存在同名但是不同机构人员");
						user.setFindUserCount(orgUsers.size());
					} else {
						user.setNote("不存在相同姓名人员");
						user.setFindUserCount(0);
					}
				} else {
					List<OrgUser> orgUsers = userService.searchAll(conditions);
					if (orgUsers != null && orgUsers.size() > 0) {
						if (orgUsers.size() > 1) {
							user.setNote("存在相同机构多个同名人员");
							user.setFindUserCount(orgUsers.size());
						} else {
							String id = orgUsers.get(0).getIdentification();
							if (id == null || id.trim().length() == 0) {
								user.setNote("存在相同机构同名人员，并身份证为空");
								user.setFindUserCount(1);
								count++;
								
								OrgUser ou = new OrgUser();
								ou.setId(orgUsers.get(0).getId());
								ou.setIdentification(identification);
								userService.updateSelective(ou);
								
							} else if (identification.equals(id)) {
								user.setNote("存在相同机构同名人员，身份证相等");
								user.setFindUserCount(1);
							} else {
								user.setNote("存在相同机构同名人员，但身份证不相等");
								user.setFindUserCount(1);
							}
						}
					} else {
						user.setNote("不存在相同姓名相同机构人员");
						user.setFindUserCount(0);
					}
				}
			}
		}

		System.out.println("导入身份证人数：" + count);
		writeExcel("d:/导出人员.xlsx", users);

		return true;
	};

	private static final WriteRow writeRow = DefaultWriteRow.createWriteRow(ImportUser.class, null);

	private void writeExcel(String path, List<ImportUser> users) {
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		try (FileOutputStream out = new FileOutputStream(path)) {
			ExcelWriter<ImportUser> writer = new ExcelWriter<>(workbook, writeRow);
			writer.openNewSheet("单位下属科室考评表");
			writer.write(users);
			writer.output(out);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("导出Excel失败", e);
		}
	}

	public int order() {
		return 9999;
	}
	
	public static class ImportUser {

		@ReadProperty(cellIndex = 0)
		@WriteProperty(cellIndex = 0, name = "序号", width = 15)
		private String index;

		@ReadProperty(cellIndex = 1)
		@WriteProperty(cellIndex = 1, name = "姓名", width = 30)
		private String name;

		@ReadProperty(cellIndex = 2)
		@WriteProperty(cellIndex = 2, name = "身份证", width = 30)
		private String identification;

		@ReadProperty(cellIndex = 3)
		@WriteProperty(cellIndex = 3, name = "所在单位", width = 45)
		private String unitName;

		@WriteProperty(cellIndex = 4, name = "身份证是否存在", width = 10)
		private String hasIdInSystem;

		@WriteProperty(cellIndex = 5, name = "是否同一个人", width = 10)
		private String isSameUser;

		@WriteProperty(cellIndex = 6, name = "找到人数", width = 10)
		private Integer findUserCount;

		@WriteProperty(cellIndex = 7, name = "说明", width = 45)
		private String note;

		public String getIndex() {
			return index;
		}

		public void setIndex(String index) {
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIdentification() {
			return identification;
		}

		public void setIdentification(String identification) {
			this.identification = identification;
		}

		public String getUnitName() {
			return unitName;
		}

		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public String getHasIdInSystem() {
			return hasIdInSystem;
		}

		public void setHasIdInSystem(String hasIdInSystem) {
			this.hasIdInSystem = hasIdInSystem;
		}

		public String getIsSameUser() {
			return isSameUser;
		}

		public void setIsSameUser(String isSameUser) {
			this.isSameUser = isSameUser;
		}

		public Integer getFindUserCount() {
			return findUserCount;
		}

		public void setFindUserCount(Integer findUserCount) {
			this.findUserCount = findUserCount;
		}
	}

}
