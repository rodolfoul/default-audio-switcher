package com.imm

import com.sun.jna.Structure
import com.sun.jna.platform.win32.WTypes
import com.sun.jna.platform.win32.WinDef

@Structure.FieldOrder("VARTYPE", "wReserved1", "wReserved2", "wReserved3", "pwszVal")
open class PROPVARIANT : Structure() {
	class REFPROPVARIANT : PROPVARIANT(), Structure.ByReference

	@JvmField
	var VARTYPE: Short = 0

	@JvmField
	var wReserved1 = WinDef.WORD(0)
	@JvmField
	var wReserved2 = WinDef.WORD(0)
	@JvmField
	var wReserved3 = WinDef.WORD(0)

	@JvmField
	var pwszVal = WTypes.LPWSTR()
}