package com

import com.sun.jna.Pointer
import com.sun.jna.ptr.PointerByReference

class IMMDevice(pointer: Pointer) : ComUnknown(pointer) {

	fun GetId(): String {
		val result = PointerByReference()
		comCall(5, arrayOf(result))
		return result.value.getWideString(0)
	}

	/*
	 * HRESULT OpenPropertyStore(
	 *   DWORD          stgmAccess,
	 *   cominterfaces.IPropertyStore **ppProperties
	 * );
	 */
	fun OpenPropertyStore(stgmAccess: STGM): IPropertyStore {
		val result = PointerByReference()
		comCall(4, arrayOf(stgmAccess.ordinal, result))

		return IPropertyStore(result.value)
	}
}