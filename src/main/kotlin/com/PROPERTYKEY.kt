package com

import com.sun.jna.Structure
import com.sun.jna.platform.win32.Guid
import com.sun.jna.platform.win32.WinDef

/*
typedef struct _tagpropertykey {
  GUID  fmtid;
  DWORD pid;
} PROPERTYKEY;
 */
@Structure.FieldOrder("fmtid", "pid")
open class PROPERTYKEY : Structure() {
	class REFPROPERTYKEY : PROPERTYKEY(), Structure.ByReference

	@JvmField
	var fmtid: Guid.GUID? = null

	@JvmField
	var pid: WinDef.DWORD? = null
}