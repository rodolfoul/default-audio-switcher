package com.imm

import com.sun.jna.Pointer

class IPropertyStore(pointer: Pointer) : ComUnknown(pointer) {
	/*
     * HRESULT GetValue(
     *   [in]  REFPROPERTYKEY key,
     *   [out] PROPVARIANT    *pv
     * );
	 */
	fun GetValue(pKey: PROPERTYKEY): String {
		var refpropvariant = PROPVARIANT.REFPROPVARIANT()
		comCall(5, arrayOf(pKey, refpropvariant))

		return refpropvariant.pwszVal.value
	}
}