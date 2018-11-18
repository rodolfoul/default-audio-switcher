package com

import com.sun.jna.Pointer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class IPropertyStore(pointer: Pointer) : ComUnknown(pointer) {
	/*
     * HRESULT GetValue(
     *   [in]  REFPROPERTYKEY key,
     *   [out] PROPVARIANT    *pv
     * );
	 */
	fun GetValue(pKey: PROPERTYKEY): String {
		val result = ByteArray(12) //Size of PROPVARIANT struct when getting description values
		comCall(5, arrayOf(pKey, result))

		// pwszVal, which is a pointer to wide-character string, stays in the position 8..11 of the byte array
		return Pointer(ByteBuffer.wrap(result.sliceArray(8..11))
				.order(ByteOrder.nativeOrder()) //Get pointer from pwsVal
				.int
				.toLong()) // Create JNA Pointer
				.getWideString(0) // Get Wide Char array pointed by it
	}
}