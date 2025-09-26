# meta-utils

A Yocto/OpenEmbedded layer providing utility recipes for system administration and certificate management.

## Description

This layer contains recipes for various system utilities that are not available in the standard OpenEmbedded layers or require specific adaptations for cross-compilation environments.

## Included Recipes

### sscg (Simple Signed Certificate Generator)
- **Version**: 3.0.8
- **Description**: Tool for generating x.509 certificates quickly without needing to understand complex OpenSSL commands
- **Homepage**: https://github.com/sgallagher/sscg
- **License**: GPL-3.0-or-later

### ding-libs (DING is not GNU helper libraries)
- **Version**: 0.6.2
- **Description**: Set of utility libraries used by SSSD and FreeIPA, including libpath_utils, libdhash, libcollection, libref_array, and libini_config
- **Homepage**: https://github.com/SSSD/ding-libs
- **License**: LGPL-3.0-or-later

## Dependencies

### Required Layers
- **openembedded-core**: Core OpenEmbedded layer
- **meta-openembedded/meta-networking**: Required for libtalloc dependency used by sscg

### Build Dependencies
The recipes will automatically handle their build dependencies through the Yocto dependency system.

## Installation

1. Clone this layer to your Yocto build environment:
   ```bash
   git clone <repository-url> path/to/meta-utils
   ```

2. Add the layer to your `bblayers.conf`:
   ```bash
   bitbake-layers add-layer path/to/meta-utils
   ```

   Or manually add to `conf/bblayers.conf`:
   ```
   BBLAYERS ?= " \
       /path/to/poky/meta \
       /path/to/poky/meta-poky \
       /path/to/poky/meta-yocto-bsp \
       /path/to/meta-openembedded/meta-oe \
       /path/to/meta-openembedded/meta-networking \
       /path/to/meta-utils \
   "
   ```

## Usage

### Building Individual Recipes

```bash
# Build ding-libs first (dependency for sscg)
bitbake ding-libs

# Build sscg
bitbake sscg
```

### Adding to Your Image

Add to your image recipe or `local.conf`:

```bash
IMAGE_INSTALL:append = " sscg"
```

For development packages:
```bash
IMAGE_INSTALL:append = " sscg ding-libs-dev"
```

## Cross-Compilation Notes

### SSCG Manpages
By default, manual page generation is disabled for cross-compilation to avoid issues with help2man trying to execute cross-compiled binaries. This is handled automatically by the included patch.

### Test Suite
Tests are disabled by default for cross-compilation environments. They can be enabled if needed but may require additional setup.

## Compatibility

### Tested Yocto Releases
- **Scarthgap (5.0.x)** ✅ Tested and working

### Supported Architectures
- x86_64
- ARM (armv7a, armv8a)
- Other architectures should work but haven't been specifically tested

## Package Details

### SSCG Packages
- **sscg**: Main executable
- Runtime dependency on openssl-bin

### Ding-libs Packages
The ding-libs recipe creates separate packages for each library:
- **libpath-utils1** / **libpath-utils-dev**: Filesystem path utilities
- **libdhash1** / **libdhash-dev**: Dynamic hash table
- **libcollection4** / **libcollection-dev**: Collection data structure
- **libref-array1** / **libref-array-dev**: Reference-counted array
- **libbasicobjects0** / **libbasicobjects-dev**: Basic object types
- **libini-config5** / **libini-config-dev**: INI file parser

## Troubleshooting

### Common Issues

1. **Missing meta-networking layer**:
   ```
   ERROR: Nothing PROVIDES 'libtalloc'
   ```
   **Solution**: Add meta-openembedded/meta-networking to your bblayers.conf

2. **Dependency path_utils not found**:
   ```
   ERROR: Dependency "path_utils" not found
   ```
   **Solution**: Build ding-libs first: `bitbake ding-libs`

3. **Help2man cross-compilation error**:
   ```
   help2man: can't get `--help' info from sscg
   ```
   **Solution**: This is handled automatically by the included patch. Ensure the patch is applied.

### Build Order
Always build in this order:
1. `bitbake ding-libs`
2. `bitbake sscg`

## Contributing

Contributions are welcome! Please ensure:
- Recipes follow OpenEmbedded coding standards
- Cross-compilation compatibility is maintained
- Updates are tested on supported Yocto releases

## License

The recipes in this layer are provided under their respective upstream licenses:
- SSCG: GPL-3.0-or-later
- ding-libs: LGPL-3.0-or-later

Recipe files themselves are provided under MIT license unless otherwise specified.

## Support

For issues related to:
- **Recipe functionality**: Open an issue in this repository
- **Upstream software bugs**: Report to the respective upstream projects
- **Yocto/OpenEmbedded usage**: Consult the official Yocto documentation

---

**Layer Maintainer**: Sylvain Pastor (sylvain.pastor@laposte.net)  
**Last Updated**: September 2025  
**Yocto Compatibility**: Scarthgap (5.0.x)