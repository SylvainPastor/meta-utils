SUMMARY = "DING is not GNU helper libraries for SSSD and FreeIPA"
DESCRIPTION = "A set of utility libraries used by System Security Services Daemon (SSSD) and FreeIPA, including libpath_utils, libdhash, libcollection, libref_array, and libini_config."
HOMEPAGE = "https://github.com/SSSD/ding-libs"
SECTION = "libs"

LICENSE = "LGPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
					file://COPYING.LESSER;md5=0ce682cee9e5b35c71c890b0458423f1"

SRC_URI = "git://github.com/SSSD/ding-libs.git;branch=master;protocol=https"
SRCREV  = "6a856ccc8dc151dddb6f1f9f11467875ea3d2206"

S = "${WORKDIR}/git"

inherit autotools pkgconfig gettext
DEPENDS = "pkgconfig-native gettext-native"

# in the autotools class there is EXTRA_AUTORECONF += "--exclude=autopoint"
# override EXTRA_AUTORECONF to enable autopoint by default
EXTRA_AUTORECONF = ""

# Enable all components by default, but disable tests for cross-compilation
EXTRA_OECONF = " \
	--enable-shared \
	--disable-static \
"

# Create separate packages for each library
PACKAGES =+ " \
	libpath-utils1 libpath-utils-dev \
	libdhash1 libdhash-dev \
	libcollection4 libcollection-dev \
	libref-array1 libref-array-dev \
	libbasicobjects0 libbasicobjects-dev \
	libini-config5 libini-config-dev \
"

# Main package files
FILES:${PN} = ""
FILES:${PN}-dev = ""

# libpath_utils files
FILES:libpath-utils1 = "${libdir}/libpath_utils.so.*"
FILES:libpath-utils-dev = " \
	${includedir}/path_utils.h \
	${libdir}/libpath_utils.so \
	${libdir}/pkgconfig/path_utils.pc \
"

# libdhash files  
FILES:libdhash1 = "${libdir}/libdhash.so.*"
FILES:libdhash-dev = " \
	${includedir}/dhash.h \
	${libdir}/libdhash.so \
	${libdir}/pkgconfig/dhash.pc \
"

# libcollection files
FILES:libcollection4 = "${libdir}/libcollection.so.*" 
FILES:libcollection-dev = " \
	${includedir}/collection*.h \
	${libdir}/libcollection.so \
	${libdir}/pkgconfig/collection.pc \
"

# libref_array files
FILES:libref-array1 = "${libdir}/libref_array.so.*"
FILES:libref-array-dev = " \
	${includedir}/ref_array.h \
	${libdir}/libref_array.so \
	${libdir}/pkgconfig/ref_array.pc \
"

# libbasicobjects files
FILES:libbasicobjects0 = "${libdir}/libbasicobjects.so.*"
FILES:libbasicobjects-dev = " \
	${includedir}/simplebuffer.h \
	${libdir}/libbasicobjects.so \
	${libdir}/pkgconfig/basicobjects.pc \
"

# libini_config files
FILES:libini-config5 = "${libdir}/libini_config.so.*"
FILES:libini-config-dev = " \
	${includedir}/ini_*.h \
	${libdir}/libini_config.so \
	${libdir}/pkgconfig/ini_config.pc \
"

# Runtime dependencies for dev packages
RDEPENDS:libpath-utils-dev = "libpath-utils1 (= ${EXTENDPKGV})"
RDEPENDS:libdhash-dev = "libdhash1 (= ${EXTENDPKGV})"  
RDEPENDS:libcollection-dev = "libcollection4 (= ${EXTENDPKGV})"
RDEPENDS:libref-array-dev = "libref-array1 (= ${EXTENDPKGV})"
RDEPENDS:libbasicobjects-dev = "libbasicobjects0 (= ${EXTENDPKGV})"
RDEPENDS:libini-config-dev = "libini-config5 (= ${EXTENDPKGV})"

BBCLASSEXTEND = "native"