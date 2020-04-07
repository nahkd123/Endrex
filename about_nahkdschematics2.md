# nahkdSchematics2
This is the schematic format, created by me. While we're currently have the WorldEdit schematic, I found that it doesn't compatible with
Slimefun at all.

# What makes nahkdSchematics2 different?
- Support extensions (so you can save custom block inside schematic)
- Uses more storage space (instead of block per byte, I decide to use block per WORD)
- Compatible with 1.13+

# Why you SHOULD/SHOULDN'T use nahkdSchematics2?
```diff
+ Can save custom block data with extensions
+ Used by me
+ Can create custom structure and generate it (for example, the abandoned spaceship)
+ Generate loot while pasting schematic
- Way larger file size comparing with WorldEdit schematics
- Slower
- NBT Data is not supported yet :(
- idk
```

# nahkdSchematics2 Extensions:
- ```diff
    SlimefunSchematicExtension
  + Allow to save custom block data to schematic
  - Still uses JSON format, which is not good
  ```

# Todos:
- Instead of using block per WORD, I'll use block per [VarInt](https://developers.google.com/protocol-buffers/docs/encoding) value,
which saves quite a lot of storage spaces.
