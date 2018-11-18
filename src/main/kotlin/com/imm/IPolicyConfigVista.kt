package com.imm

import com.sun.jna.platform.win32.COM.COMUtils
import com.sun.jna.platform.win32.ObjBase
import com.sun.jna.platform.win32.Ole32
import com.sun.jna.platform.win32.Ole32Util
import com.sun.jna.platform.win32.WTypes
import com.sun.jna.ptr.PointerByReference

// Basic example:
// https://social.msdn.microsoft.com/Forums/vstudio/en-US/3c437708-0e90-483d-b906-63282ddd2d7b/change-audio-input?forum=vbgeneral
//
// Device enumeration:
// https://docs.microsoft.com/en-us/windows/desktop/CoreAudio/mmdevice-api
class IPolicyConfigVista : ComUnknown() {
	init {
		val cPolicyConfigVistaClient = Ole32Util.getGUIDFromString("{294935ce-f637-4e7c-a41b-ab255460b862}")
		val iPolicyConfigVista = Ole32Util.getGUIDFromString("{568b9108-44bf-40b4-9006-86afe5b5a620}")

		val policyConfigPointer = PointerByReference()
		COMUtils.checkRC(Ole32.INSTANCE.CoCreateInstance(cPolicyConfigVistaClient,
				null,
				ObjBase.CLSCTX_ALL,
				iPolicyConfigVista,
				policyConfigPointer
		))

		pointer = policyConfigPointer.value
	}

	fun SetDefaultEndpoint(deviceId: String) {
		for (role in eRole.values()) {
			comCall(12, arrayOf(WTypes.LPWSTR(deviceId), role.ordinal))
		}
	}
}