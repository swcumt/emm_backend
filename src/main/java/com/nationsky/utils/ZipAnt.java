package com.nationsky.utils;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class ZipAnt {

	private static final Project DEFAULT_PROJECT = new Project();

	public static void unZip(String folderPath, String zipFilePath) {
		File zipFile = new File(zipFilePath);
		File folder = new File(folderPath);
		unZip(folder, zipFile);
	}

	public static void unZip(File folder, File zipFile) {
		if (zipFile.exists()) {
			Expand expand = new Expand();
			expand.setProject(DEFAULT_PROJECT);
			expand.setSrc(zipFile);
			expand.setDest(folder);
			expand.execute();
		} else {
			System.out.println("需要解压的文件不存在");
		}
	}

	public static void zip(String folderPath, String zipFilePath) {
		File zipFile = new File(zipFilePath);
		File folder = new File(folderPath);
		zip(folder, zipFile);
	}

	public static void zip(File folder, File zipFile) {
		if (folder.exists()) {
			FileSet fileSet = new FileSet();
			fileSet.setProject(DEFAULT_PROJECT);
			fileSet.setDir(folder);

			Zip zip = new Zip();
			zip.setProject(DEFAULT_PROJECT);
			zip.setDestFile(zipFile);
			zip.addFileset(fileSet);
			zip.execute();
		} else {
			System.out.println("需要压缩的目录不存在");
		}
	}

	public static void main(String[] args) {
		String folderPath = "F:\\aaa";
		String zipFilePath = "F:\\ddd.zip";
		// zip(folderPath, zipFilePath);
		unZip(folderPath, zipFilePath);
	}

}
