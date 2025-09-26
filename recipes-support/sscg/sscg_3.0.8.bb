SUMMARY = "Simple Signed Certificate Generator"
DESCRIPTION = "SSCG makes it easy to generate usable, signed certificates quickly without needing to understand complex openssl, certtool or certutil commands."
HOMEPAGE = "https://github.com/sgallagher/sscg"
SECTION = "utils"

LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=06b2e25ecfb8731bfa17b888103be94a"

SRC_URI = "git://github.com/sgallagher/sscg.git;branch=main;protocol=https \
	file://0001-Make-manpages-optional-for-cross-compilation.patch"
SRCREV  = "d95bb14960dbcb9913aac7aa65677d0827e37ce7"

S = "${WORKDIR}/git"

# Compilation dependencies:
# - libtalloc: Memory pool allocator used by sscg (meta-networking layer required)
# - popt: Command line options parsing (in openembedded-core)
# - ding-libs: Required for path_utils (libpath_utils-dev)
# - openssl: Crypto libraries and tools
DEPENDS = " \
	openssl \
	libtalloc \
	popt \
	ding-libs \
	meson-native \
	ninja-native \
	pkgconfig-native \
"

# Runtime dependencies
RDEPENDS:${PN} = "openssl-bin \
	libpath-utils1 \
	libtalloc"

inherit meson pkgconfig

# Meson configuration options
EXTRA_OEMESON = " \
	-Dc_std=gnu99 \
	-Dwarning_level=1 \
	-Db_asneeded=true \
	-Dwith_manpages=false \
"

# Tests require openssl binary at runtime and can be time-consuming
PACKAGECONFIG ??= ""
PACKAGECONFIG[tests] = "-Drun_slow_tests=true,-Drun_slow_tests=false,,"

# Disable tests by default for cross-compilation
EXTRA_OEMESON:append = " -Drun_slow_tests=false"

FILES:${PN} += " \
	${bindir}/sscg \
"

BBCLASSEXTEND = "native nativesdk"