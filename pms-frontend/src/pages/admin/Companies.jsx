import { useEffect, useState } from "react";

import { Button, Typography, Stack, Card, CardContent } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";

import AdminLayout from "../../layouts/AdminLayout";
import DataTable from "../../components/DataTable";
import CompanyDialog from "../../components/CompanyDialog";


import {
	getCompanies,
	createCompany,
	updateCompany,
} from "../../api/companyApi";

function Companies() {
	const [companies, setCompanies] = useState([]);
	const [open, setOpen] = useState(false);
	const [isEdit, setIsEdit] = useState(false);
	const [company, setCompany] = useState({});

	const fetchCompanies = async () => {
	try {
		const response = await getCompanies();
		setCompanies(response.data);
	} catch (error) {
		console.error("Failed to load companies:", error);
	}
	};

	useEffect(() => {
	const loadCompanies = async () => {
		try {
		const response = await getCompanies();
		setCompanies(response.data);
		} catch (error) {
		console.error("Failed to load companies:", error);
		}
	};

	loadCompanies();
	}, []);

	const handleCreate = () => {
	setCompany({});
	setIsEdit(false);
	setOpen(true);
	};

	const handleEdit = (row) => {
	setCompany(row);
	setIsEdit(true);
	setOpen(true);
	};

	const handleSave = async () => {
	try {
		if (isEdit) {
		await updateCompany(company.companyId, company);
		} else {
		await createCompany(company);
		}

		setOpen(false);
		await fetchCompanies();
	} catch (error) {
		console.error("Failed to save company:", error);
	}
	};

	const rows = companies.map((company) => ({
	...company,
	id: company.companyId,
	jobsCount: company.jobs?.length || 0,
	}));

	const columns = [
	{
		field: "companyId",
		headerName: "ID",
		width: 80,
	},
	{
		field: "companyName",
		headerName: "Company",
		width: 220,
	},
	{
		field: "email",
		headerName: "Email",
		width: 250,
	},
	{
		field: "location",
		headerName: "Location",
		width: 150,
	},
	{
		field: "jobsCount",
		headerName: "Jobs",
		width: 100,
	},
	{
		field: "actions",
		headerName: "Actions",
		width: 150,
		renderCell: (params) => (
		<Button
			variant="outlined"
			size="small"
			onClick={() => handleEdit(params.row)}
		>
			Edit
		</Button>
		),
	},
	];

	return (
    <AdminLayout>
      <Stack direction="row" justifycontent="space-between" mb={3}>
        <Typography variant="h4">Companies</Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleCreate}
          sx={{ ml: 8 }}
        >
          Add Company
        </Button>
      </Stack>

      <Card>
        <CardContent>
          <DataTable rows={rows} columns={columns} />
        </CardContent>
      </Card>

      <CompanyDialog
        open={open}
        onClose={() => setOpen(false)}
        company={company}
        setCompany={setCompany}
        onSave={handleSave}
        isEdit={isEdit}
      />
    </AdminLayout>
  );
}

export default Companies;
