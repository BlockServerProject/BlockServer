BlockServerFormat
===
This markdown file records the format of BlockServerFormat (`.bsf`) files.

Current version: `0x00`

## Usages
BlockServerFormat can be used for various database files. The following table lists the usages and their IDs.

|  Name  | ID (hex) |
| :----: | :------: |
| Player |   `00`   |

## Standard Data Types
|  Type  | Name | Description | Length | Range |
| :----: | :---: | :---- | :--: | :--: |
| Number | `Byte` | An integer | 1 | 0 to 255 |
| Number | `SignedByte` | An integer that is saved 256 larger if smaller than 0 | 1 | -128 to 127 |
| Number | `Short` | An integer | 2 | 0 to 65536 |
| Number | `SignedShort` | An integer that is saved 65536 larger if smaller than 0 | 2 | -32768 to 32767 |
| Number | `Int` | An integer | 4 | 0 to 4294967296 |
| Number | `SignedInt` | An integer that is saved 2147483648 larger if smaller than 0 | 4 | -2147483648 to 2147483647 |
| Number | `Long` | An integer | 8 | 0 to 18446744073709551616 |
| Number | `SignedLong` | An integer that is saved 18446744073709551616 larger if smaller than 0 | 8 | -9223372036854775808 to 9223372036854775807 |
| Number | `Double` | An unsigned double | 8 | undefined |
| Number | `SignedDouble` | A signed double | 8 | undefined |
|  Text  | `Char` | A character | 1 | ASCII 0 to 255 |
|  Text  | `BString` | A `Byte` of string length + the string literally | 1 + length of string | any strings of 0 to 255 bytes |
|  Text  | `String` | A `Short` of string length + the string literally | 1 + length of string | any strings of 0 to 65535 bytes |
|  Text  | `RawString` | the string literally | length of string | any strings |

## Non-standard Data Types
Non-standard data types are combinations of standard data types, used to save data for non-essential objects in BlockServer or Java.

### List<T>
```yaml
Int: number of elements in this list
-> for each element:
  T: The value of the element
```

### Map<K, V>
```yaml
Int: number of elements in this map
-> for each element:
  K: The key of the element
  V: The value of the element
```

### Inventory
Same format as `List<Item>`.

## Item
```yaml
Short: ID of the item
Byte: Damage of the item
Byte: Count of the item
Map<String, String>: The metadatas stored within the item
```

## Format
All BlockServerFormat files have a common header and footer.

```yaml
# Header
RawString: 0x50b5ff # 5([s]tart)0([o]f)b(lock)5([s]erver)f(ormat)f(ile)
Byte: BSF version ID
Byte: ID of the type
# Footer
RawString: 0xe0b5ff # e(nd)0([o]f)b(lock)5([s]erver)f(ormat)f(ile)
```
