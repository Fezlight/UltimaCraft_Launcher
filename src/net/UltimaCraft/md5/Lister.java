package net.UltimaCraft.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.UltimaCraft.Link;
import net.UltimaCraft.Util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Lister {

	public static void run(File workingDirectory) {

		System.out.println("Starting Listing and Deleting bad mods...");
		try {
			Set<String> lmd5 = localMd5Listing();
			Set<String> dmd5 = distantMd5Listing();

			System.out.println("Local Files ETags : " + lmd5);
			System.out.println("Server Files ETags : " + dmd5);

			if (lmd5.equals(dmd5)) {
				System.out.println("No obsoletes files in local directory.");
				// LauncherV2.var1 = 0;
			} else {
				/*
				 * lmd5.removeAll(dmd5);
				 * 
				 * List<String> obsoleteFilesList = new ArrayList<String>();
				 * obsoleteFilesList.addAll(lmd5);
				 * 
				 * recurseDelete(modsDir, obsoleteFilesList);
				 */
				System.out.println("Mods Client et Serveur Différents");

			}
		} catch (Exception e) {
			System.out.println("Can't run deleting old mods method : " + e);
		}

	}

	@SuppressWarnings("unused")
	private static void recurseDelete(File it, List<String> lmd5)
			throws IOException {

		if (it.isDirectory()) {
			if (it.list().length == 0) {
				if (it.delete()) {
					// Success
				}
			} else {
				for (File f : it.listFiles())
					recurseDelete(f, lmd5);
			}
		} else {

			FileInputStream fis = new FileInputStream(it);
			String md5 = org.apache.commons.codec.digest.DigestUtils
					.md5Hex(fis);
			fis.close();
			if (lmd5.contains(md5) && !it.getName().endsWith(".cfg")
					&& !it.getName().endsWith(".json")
					&& !it.getName().endsWith(".properties")) {
				System.out.println("Preparing to delete " + it.getName());
				if (it.delete()) {
					System.out.println(it.getName() + " deleted !");
				}
			}
		}
	}

	static Set<String> distantMd5Listing() throws Exception {

		List<String> distantMd5List = new ArrayList<String>();

		URL resourceUrl = new URL(Link.URL_BASE_LOCALHOST + "test/");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(resourceUrl.openStream());

		NodeList nl = doc.getElementsByTagName("Contents");

		for (int i = 0; i < nl.getLength(); i++) {

			NodeList md5Nlc = doc.getElementsByTagName("ETag");
			Element md5Elements = (Element) md5Nlc.item(i);
			String md5TagValue = md5Elements.getChildNodes().item(0)
					.getNodeValue().replace("\"", "");
			distantMd5List.add(md5TagValue);
			System.out.println("Distant File checked with " + md5TagValue
					+ " hash value");
		}

		Set<String> distantSet = new HashSet<String>();
		distantSet.addAll(distantMd5List);
		distantMd5List.clear();
		distantMd5List.addAll(distantSet);

		return distantSet;
	}

	static Set<String> localMd5Listing() {

		List<String> localMd5List = new ArrayList<String>();

		File modsDir = new File(Util.getWorkingDirectory(), "mods");

		try {
			recurseMD5(modsDir, localMd5List);
		} catch (Exception e) {
			System.out.println("Couldn't check local files hashes : " + e);
		}
		// localMd5List.add("d41d8cd98f00b204e9800998ecf8427e");//Default MD5
		// Hash for directories = [No String]

		Set<String> localSet = new HashSet<String>();
		localSet.addAll(localMd5List);
		localMd5List.clear();
		localMd5List.addAll(localSet);

		return localSet;
	}

	static void recurseMD5(File it, List<String> hashes) {
		if (it.isDirectory()) {
			for (File f : it.listFiles())
				if (!f.getName().endsWith(".properties"))
					recurseMD5(f, hashes);
		} else {
			try {
				FileInputStream fis = new FileInputStream(it);
				if (it.isDirectory()) {
				} else {
					String md5 = org.apache.commons.codec.digest.DigestUtils
							.md5Hex(fis);
					fis.close();
					hashes.add(md5);
					System.out.println("Local File " + it + " checked with "
							+ md5 + " hash value");
				}
			} catch (IOException e) {
				System.out.println("Couldn't check local file hash : " + e);
			}

		}
	}

}