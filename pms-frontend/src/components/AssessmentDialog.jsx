import {
	Dialog,
	DialogTitle,
	DialogContent,
	DialogActions,
	Button,
	TextField,
	MenuItem,
	Stack,
} from "@mui/material";

function AssessmentDialog({
	open,
	onClose,
	assessment,
	setAssessment,
	jobs,
	onSave,
}) {
	const handleChange = (e) => {
		setAssessment({
			...assessment,
			[e.target.name]: e.target.value,
		});
	};

	return (
		<Dialog open={open} onClose={onClose} fullWidth maxWidth="md">
			<DialogTitle>Schedule Assessment</DialogTitle>

			<DialogContent>
				<Stack spacing={2} mt={1}>
					<TextField
						select
						label="Job"
						name="jobId"
						value={assessment.jobId || ""}
						onChange={handleChange}
					>
						{jobs.map((job) => (
							<MenuItem key={job.jobId} value={job.jobId}>
								{job.title}
							</MenuItem>
						))}
					</TextField>

					<TextField
						label="Assessment Name"
						name="assessmentName"
						value={assessment.assessmentName || ""}
						onChange={handleChange}
					/>

					<TextField
						select
						label="Assessment Type"
						name="assessmentType"
						value={assessment.assessmentType || ""}
						onChange={handleChange}
					>
						<MenuItem value="TEST">TEST</MenuItem>

						<MenuItem value="INTERVIEW">INTERVIEW</MenuItem>
					</TextField>

					<TextField
						type="date"
						label="Date"
						name="assessmentDate"
						value={assessment.assessmentDate || ""}
						onChange={handleChange}
						InputLabelProps={{
							shrink: true,
						}}
					/>

					<TextField
						type="time"
						label="Time"
						name="assessmentTime"
						value={assessment.assessmentTime || ""}
						onChange={handleChange}
						InputLabelProps={{
							shrink: true,
						}}
					/>

					<TextField
						label="Total Marks"
						type="number"
						name="totalMarks"
						value={assessment.totalMarks || ""}
						onChange={handleChange}
					/>

					<TextField
						label="Interview Percentage"
						type="number"
						name="interviewPercentage"
						value={assessment.interviewPercentage || ""}
						onChange={handleChange}
					/>
				</Stack>
			</DialogContent>

			<DialogActions>
				<Button onClick={onClose}>Cancel</Button>

				<Button variant="contained" onClick={onSave}>
					Save
				</Button>
			</DialogActions>
		</Dialog>
	);
}

export default AssessmentDialog;
