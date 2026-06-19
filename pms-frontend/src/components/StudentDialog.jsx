import {
	Dialog,
	DialogTitle,
	DialogContent,
	DialogActions,
	Button,
	TextField,
	Stack,
} from "@mui/material";

function StudentDialog({ open, onClose, student, setStudent, onSave }) {
	const handleChange = (e) => {
		setStudent({
			...student,
			[e.target.name]: e.target.value,
		});
	};

	return (
		<Dialog open={open} onClose={onClose} fullWidth maxWidth="md">
			<DialogTitle>Student Details</DialogTitle>

			<DialogContent>
				<Stack spacing={2} mt={1}>
					<TextField label="Name" value={student.user?.name || ""} disabled />

					<TextField label="Email" value={student.user?.email || ""} disabled />

					<TextField
						name="phone"
						label="Phone"
						value={student.phone || ""}
						onChange={handleChange}
					/>

					<TextField
						name="department"
						label="Department"
						value={student.department || ""}
						onChange={handleChange}
					/>

					<TextField
						name="cgpa"
						label="CGPA"
						value={student.cgpa || ""}
						onChange={handleChange}
					/>

					<TextField
						name="graduationYear"
						label="Graduation Year"
						value={student.graduationYear || ""}
						onChange={handleChange}
					/>

					<TextField
						name="address"
						label="Address"
						value={student.address || ""}
						onChange={handleChange}
					/>
					<TextField
					label="Skills"
					value={
						student.skills
						?.map(
							(skill) =>
							`${skill.skillName} (${skill.proficiencyLevel})`
						)
						.join(", ") || ""
					}
					multiline
					rows={3}
					disabled
					/>

					<TextField
					label="Resume"
					value={
						student.resume?.fileName ||
						"No Resume Uploaded"
					}
					disabled
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

export default StudentDialog;
