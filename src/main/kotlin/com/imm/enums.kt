package com.imm

import com.sun.jna.platform.win32.Guid
import com.sun.jna.platform.win32.WinDef

enum class EDataFlow {
	eRender,
	eCapture,
	eAll,
	EDataFlow_enum_count
}

enum class DeviceState(val value: Long) {
	DEVICE_STATE_ACTIVE(0x00000001),
	DEVICE_STATE_DISABLED(0x00000002),
	DEVICE_STATE_NOTPRESENT(0x00000004),
	DEVICE_STATE_UNPLUGGED(0x00000008),
	DEVICE_STATEMASK_ALL(0x0000000f),
}

enum class eRole {
	eConsole,
	eMultimedia,
	eCommunications,
}

enum class STGM(val value: Int) {
	STGM_READ(0x00000000)
}

enum class PKEY(val value: Int) {
	PKEY_Device_FriendlyName(8),
	reg_name(2)
}

val PKEY_Device_FriendlyName = PROPERTYKEY.REFPROPERTYKEY().apply {
	fmtid = Guid.GUID.fromString("{A45C254E-DF1C-4EFD-8020-67D146A850E0}")
	pid = WinDef.DWORD(14)
}