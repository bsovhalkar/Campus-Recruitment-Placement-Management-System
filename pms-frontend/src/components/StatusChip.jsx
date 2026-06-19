import Chip from "@mui/material/Chip";

function StatusChip({ status }) {
	const getColor = () => {
		switch (status) {
			case "SELECTED":
				return "success";

			case "REJECTED":
				return "error";

			case "APPLIED":
				return "primary";

			case "WITHDRAWN":
				return "default";

			case "SHORTLISTED_FOR_TEST":
				return "warning";

			case "SHORTLISTED":
				return "warning";

			case "SCHEDULED":
				return "info";

			case "COMPLETED":
				return "success";

			default:
				return "default";
		}
	};

	return <Chip label={status} color={getColor()} size="small" />;
}

export default StatusChip;
