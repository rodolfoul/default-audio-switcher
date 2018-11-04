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