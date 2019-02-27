package com.paladin.data.generate.build;

import freemarker.template.Configuration;

public class FreemarkerUtil {

	private final static Configuration templateConfig;

	static {
		templateConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		templateConfig.setClassForTemplateLoading(FreemarkerUtil.class, "");
	}

//	public static Template getTemplate(String name) {
//		try {
//			return templateConfig.getTemplate(name);
//		} catch (IOException e) {
//			throw new RuntimeException("获取Controller模板失败", e);
//		}
//	}

}
