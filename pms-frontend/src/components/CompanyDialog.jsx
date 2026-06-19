import {
	Dialog,
	DialogTitle,
	DialogContent,
	DialogActions,
	Button,
	TextField,
	Stack,
} from "@mui/material";

function CompanyDialog({ open, onClose, company, setCompany, onSave, isEdit }) {
	const handleChange = (e) => {
		setCompany({
			...company,
			[e.target.name]: e.target.value,
		});
	};

	return (
		<Dialog open={open} onClose={onClose} fullWidth maxWidth="md">
			<DialogTitle>{isEdit ? "Edit Company" : "Add Company"}</DialogTitle>

			<DialogContent>
				<Stack spacing={2} mt={1}>
					<TextField
						label="Company Name"
						name="companyName"
						value={company.companyName || ""}
						onChange={handleChange}
						fullWidth
					/>

					<TextField
						label="Email"
						name="email"
						value={company.email || ""}
						onChange={handleChange}
						fullWidth
					/>

					<TextField
						label="Website"
						name="website"
						value={company.website || ""}
						onChange={handleChange}
						fullWidth
					/>

					<TextField
						label="Location"
						name="location"
						value={company.location || ""}
						onChange={handleChange}
						fullWidth
					/>

					<TextField
						label="Description"
						name="description"
						value={company.description || ""}
						onChange={handleChange}
						multiline
						rows={4}
						fullWidth
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

export default CompanyDialog;
