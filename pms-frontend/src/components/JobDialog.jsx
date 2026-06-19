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

function JobDialog({ open, onClose, job, setJob, companies, onSave, isEdit }) {
	const handleChange = (e) => {
		const { name, value } = e.target;

		setJob({
			...job,
			[name]: value,
		});
	};

	return (
		<Dialog open={open} onClose={onClose} fullWidth maxWidth="md">
			<DialogTitle>{isEdit ? "Edit Job" : "Add Job"}</DialogTitle>

			<DialogContent>
				<Stack spacing={2} mt={1}>
					<TextField
						label="Title"
						name="title"
						value={job.title || ""}
						onChange={handleChange}
					/>

					<TextField
						label="Description"
						name="description"
						multiline
						rows={3}
						value={job.description || ""}
						onChange={handleChange}
					/>

					<TextField
						label="Package Offered"
						name="packageOffered"
						type="number"
						value={job.packageOffered || ""}
						onChange={handleChange}
					/>

					<TextField
						label="Minimum CGPA"
						name="minimumCgpa"
						type="number"
						value={job.minimumCgpa || ""}
						onChange={handleChange}
					/>

					<TextField
						label="Vacancies"
						name="vacancies"
						type="number"
						value={job.vacancies || ""}
						onChange={handleChange}
					/>

					<TextField
						label="Location"
						name="location"
						value={job.location || ""}
						onChange={handleChange}
					/>

					<TextField
						label="Passing Year"
						name="passingYear"
						type="number"
						value={job.passingYear || ""}
						onChange={handleChange}
					/>

					<TextField
						label="Application Deadline"
						name="applicationDeadline"
						type="date"
						value={job.applicationDeadline || ""}
						onChange={handleChange}
						InputLabelProps={{
							shrink: true,
						}}
					/>

					<TextField
						label="Eligible Departments"
						name="eligibleDepartments"
						value={job.eligibleDepartments || ""}
						onChange={handleChange}
					/>

					<TextField
						select
						label="Company"
						name="companyId"
						value={job.companyId || ""}
						onChange={handleChange}
					>
						{companies.map((company) => (
							<MenuItem key={company.companyId} value={company.companyId}>
								{company.companyName}
							</MenuItem>
						))}
					</TextField>
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

export default JobDialog;
