package net.UltimaCraft;

import net.UltimaCraft.VersionCheck.VersionType;

public class Verification {

	private boolean principal = false;
	private boolean libraries = false;
	private boolean mods = false;
	private boolean jars = false;
	int nbEtape = 0;

	private VersionCheck principalCheck = new VersionCheck(
			VersionType.INIT_FOLDER); // Init VersionCheck object for check
										// version of boolean mainDownload
	private VersionCheck jarCheck = new VersionCheck(VersionType.JAR); // Init
																		// VersionCheck
																		// object
																		// for
																		// check
																		// version
																		// of
																		// jars
	private VersionCheck libsCheck = new VersionCheck(VersionType.LIBS); // Init
																			// VersionCheck
																			// object
																			// for
																			// check
																			// version
																			// of
																			// libs
	private VersionCheck modsCheck = new VersionCheck(VersionType.MODS); // Init
																			// VersionCheck
																			// object
																			// for
																			// check
																			// version
																			// of
																			// mods

	public Verification() {
		this.launch();
	}

	public void launch() {
		if (!principalCheck.versionOK()) {
			this.principal = true;
			nbEtape++;
		}
		if (!libsCheck.versionOK()) {
			this.libraries = true;
			nbEtape++;
		}
		if (!modsCheck.versionOK()) {
			this.mods = true;
			nbEtape++;
		}
		if (!jarCheck.versionOK()) {
			this.jars = true;
			nbEtape++;
		}
	}

	public VersionCheck getPrincipalCheck() {
		return principalCheck;
	}

	public VersionCheck getJarCheck() {
		return jarCheck;
	}

	public VersionCheck getLibsCheck() {
		return libsCheck;
	}

	public VersionCheck getModsCheck() {
		return modsCheck;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public boolean isJar() {
		return jars;
	}

	public boolean isLibraries() {
		return libraries;
	}

	public boolean isMods() {
		return mods;
	}

	public int getNbEtape() {
		return nbEtape;
	}
}
