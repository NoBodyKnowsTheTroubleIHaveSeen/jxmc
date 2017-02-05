package org.whh.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.whh.base.ControllerBase;

@Controller
public class ArticalController extends ControllerBase {
	Logger logger = LoggerFactory.getLogger(ArticalController.class);

	@RequestMapping("/common/artical")
	public String artical(String title, String chapter,String ticket,Boolean isSubscriber, Model model) {
		String content = getContent(title, chapter);
		model.addAttribute("content", content);
		model.addAttribute("title", title);
		model.addAttribute("chapter", chapter);
		model.addAttribute("chapterSize", getChapterSize(title));
		model.addAttribute("ticket", ticket);
		model.addAttribute("isSubscriber", isSubscriber);
		return "artical/articalTemplate";
	}

	private String getContent(String title, String chapter) {
		BufferedReader reader = null;
		FileInputStream stream;
		try {
			String filePath = this.getClass().getResource("/").toURI().getPath() + "/static/artical/" + title + "/"
					+ chapter + ".txt";
			stream = new FileInputStream(new File(filePath));
			reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
			String tmp = "";
			StringBuffer buffer = new StringBuffer();
			while ((tmp = reader.readLine()) != null) {
				buffer.append(tmp);
				buffer.append("\r\n");
			}
			return buffer.toString();
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	private int getChapterSize(String title) {
		try {
			String filePath = ArticalController.class.getResource("/").toURI().getPath() + "/static/artical/" + title;
			File file = new File(filePath);
			if (isNull(file)) {
				return 0;
			}
			String[] files = file.list();
			if (files == null) {
				return 0;
			}
			return files.length;
		} catch (URISyntaxException e) {
		}
		return 0;
	}

}