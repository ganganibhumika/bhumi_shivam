package com.techhive.shivamweb.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/download")
public class DownloadController {

	
	@GetMapping(value = "allow/image", produces = { "application/pdf", "application/xml", "application/json",
			"application/raw" })
	public void downLoadImage(@RequestParam String link, @RequestParam String name, HttpServletResponse response)
			throws IOException {

		String FILE_URL = link;
		InputStream in = new URL(FILE_URL).openStream();
		response.setHeader("Content-Type", "image/jpeg");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + name + ".jpeg\"");

		ServletOutputStream outputStream = response.getOutputStream();
		byte[] buffer = new byte[512];
		int l = in.read(buffer);
		while (l >= 0) {
			outputStream.write(buffer, 0, l);
			l = in.read(buffer);
		}
		outputStream.flush();
		outputStream.close();

	}

	@GetMapping(value = "allow/video", produces = { "application/pdf", "application/xml", "application/json",
			"application/raw" })
	public void downLoadVideo(@RequestParam String link, @RequestParam String name, HttpServletResponse response)
			throws IOException {
		String FILE_URL = link;
		InputStream in = new URL(FILE_URL).openStream();

		response.setHeader("Content-Type", "video/mp4");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + name + ".mp4");

		ServletOutputStream outputStream = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int l = in.read(buffer);
		while (l >= 0) {
			outputStream.write(buffer, 0, l);
			l = in.read(buffer);
		}
		outputStream.flush();
		outputStream.close();

	}

	
	@GetMapping(value = "allow/certificat", produces = { "application/pdf", "application/xml", "application/json",
	"application/raw" })
	public void downLoadcertificat(@RequestParam String link, @RequestParam String name,HttpServletResponse response)
			throws IOException {
		String FILE_URL = link;
		InputStream in = new URL(FILE_URL).openStream();
		response.setHeader("Content-Type", "application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + name + ".pdf");
		ServletOutputStream outputStream = response.getOutputStream();
				
		byte[] buffer = new byte[512];
		int l = in.read(buffer);
		while (l >= 0) {
			outputStream.write(buffer, 0, l);
			l = in.read(buffer);
		}
		outputStream.flush();
		outputStream.close();
		
		
		

	}
}
