package cuong.run.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cuong.run.service.Param;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

@Service
public class SerParam implements Param {
	@Autowired
	HttpServletRequest request;

	@Override
	public String getString(String name, String defaultValue) {
		String value = request.getParameter(name);
		return value == null ? defaultValue : value;
	}

	@Override
	public String[] getStrings(String name, String regex) {
		String value = request.getParameter(name);
		return value == null ? null : value.split(regex);
	}

	@Override
	public int getInt(String name, int defaultValue) {
		String value = getString(name, String.valueOf(defaultValue));
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException num) {
			return defaultValue;
		}
	}

	@Override
	public double getDouble(String name, double defaultValue) {
		String value = getString(name, String.valueOf(defaultValue));
		try {
			return Double.valueOf(value);
		} catch (NumberFormatException num) {
			return defaultValue;
		}
	}

	@Override
	public float getFloat(String name, Float defaultValue) {
		String value = getString(name, String.valueOf(defaultValue));
		return Float.valueOf(value);
	}

	@Override
	public boolean getBoolean(String name, boolean defaultValue) {
		String value = getString(name, String.valueOf(defaultValue));
		return Boolean.valueOf(value);
	}

	@Override
	public Date getDate(String name, String pattern) {
		String value = getString(name, "null");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(value);
		} catch (ParseException e) {
			return null;
		}
	}

	public File dir(String path) {
		File dir = new File(request.getServletContext().getRealPath(path));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	@Override
	public File save(MultipartFile file, String path) {
		File dir = dir(path);
		String filename = file.getOriginalFilename();
		File f = new File(dir, filename);
		try {
			file.transferTo(f);
			return f;
		} catch (IllegalStateException | IOException e) {
		}
		return null;
	}

	@Override
	public String saves(MultipartFile file, String path) {
		File f = save(file, path);
		return f == null ? "" : f.getName();
	}

	@Override
	public File save(String name, String path) {
		File dir = dir(path);
		Part photo;
		try {
			photo = request.getPart(name);
			File file = new File(dir, photo.getSubmittedFileName());
			photo.write(file.getAbsolutePath());
			return file;
		} catch (IOException | ServletException e) {
		}
		return null;
	}

	@Override
	public String saves(String name, String path) {
		File f = save(name, path);
		return f == null ? "" : f.getName();
	}

	@Override
	public String[] save(MultipartFile[] files, String path) throws IllegalStateException, IOException {
		File dir = dir(path);
		int filesLength = files.length;
		String[] string = new String[filesLength];

		for (int i = 0; i < filesLength; i++) {
			MultipartFile file = files[i];
			String filename = file.getOriginalFilename();
			File f = new File(dir, filename);
			file.transferTo(f);
			string[i] = f.getName();
		}
		return string;
	}

	@Override
	public String[] saves(MultipartFile[] file, String path) {
		try {
			String[] string = save(file, path);
			return string;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String save(File f) {
		return f == null ? "" : f.getName();
	}

}
