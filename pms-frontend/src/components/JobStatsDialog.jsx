import {
	Dialog,
	DialogTitle,
	DialogContent,
	Typography,
	Stack,
} from "@mui/material";

function JobStatsDialog({ open, onClose, stats }) {
	return (
		<Dialog open={open} onClose={onClose}>
			<DialogTitle>Job Statistics</DialogTitle>

			<DialogContent>
				<Stack spacing={2}>
					<Typography>Applications: {stats.applications}</Typography>

					<Typography>Shortlisted: {stats.shortlisted}</Typography>

					<Typography>
						Assessments Pending:
						{stats.assessmentsPending}
					</Typography>

					<Typography>Selected: {stats.selected}</Typography>

					<Typography>Rejected: {stats.rejected}</Typography>
				</Stack>
			</DialogContent>
		</Dialog>
	);
}
export default JobStatsDialog;
