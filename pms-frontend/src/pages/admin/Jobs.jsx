import { useEffect, useState } from "react";

import {
	Button,
	Stack,
	Typography,
  Card,
  CardContent,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";

import AdminLayout from "../../layouts/AdminLayout";
import DataTable from "../../components/DataTable";

import JobDialog from "../../components/JobDialog";
import JobStatsDialog from "../../components/JobStatsDialog";


import {
	getJobs,
	createJob,
	updateJob,
	getJobStats,
} from "../../api/jobApi";

import {
	getCompanies,
} from "../../api/companyApi";

function Jobs() {
	const [jobs, setJobs] = useState([]);
	const [companies, setCompanies] = useState([]);

	const [open, setOpen] = useState(false);
	const [isEdit, setIsEdit] = useState(false);

	const [job, setJob] = useState({});

	const [statsOpen, setStatsOpen] =
		useState(false);

	const [stats, setStats] = useState({});
  const [search, setSearch] = useState("");
  const filteredJobs = jobs.filter(
    (job) =>
      job.title.toLowerCase().includes(search.toLowerCase()) ||
      job.companyName.toLowerCase().includes(search.toLowerCase()),
  );

	const loadData = async () => {
		try {
			const [jobsRes, companiesRes] =
				await Promise.all([
					getJobs(),
					getCompanies(),
				]);

			setJobs(jobsRes.data);
			setCompanies(companiesRes.data);
		} catch (error) {
			console.error(error);
		}
	};

	useEffect(() => {
		loadData();
	}, []);

	const handleCreate = () => {
		setJob({
			title: "",
			description: "",
			packageOffered: "",
			minimumCgpa: "",
			vacancies: "",
			applicationDeadline: "",
			location: "",
			passingYear: "",
			backlogAllowed: false,
			backlogAllowedCnt: 0,
			eligibleDepartments: "",
			companyId: "",
		});

		setIsEdit(false);
		setOpen(true);
	};

	const handleEdit = (row) => {
		setJob(row);
		setIsEdit(true);
		setOpen(true);
	};

	const handleSave = async () => {
		try {
			if (isEdit) {
				await updateJob(
					job.jobId,
					job
				);
			} else {
				await createJob(job);
			}

			await loadData();

			setOpen(false);
		} catch (error) {
			console.error(error);
		}
	};

	const handleStats = async (
		jobId
	) => {
		try {
			const response =
				await getJobStats(jobId);

			setStats(response.data);
			setStatsOpen(true);
		} catch (error) {
			console.error(error);
		}
	};

	const rows = jobs.map((job) => ({
		...job,
		id: job.jobId,
	}));

	const columns = [
		{
			field: "jobId",
			headerName: "ID",
			width: 80,
		},
		{
			field: "title",
			headerName: "Title",
			width: 220,
		},
		{
			field: "companyName",
			headerName: "Company",
			width: 180,
		},
		{
			field: "packageOffered",
			headerName: "Package",
			width: 120,
			renderCell: (params) =>
				`${params.value} LPA`,
		},
		{
			field: "minimumCgpa",
			headerName: "CGPA",
			width: 100,
		},
		{
			field: "vacancies",
			headerName: "Vacancies",
			width: 120,
		},
		{
			field: "location",
			headerName: "Location",
			width: 150,
		},
		{
			field: "applicationDeadline",
			headerName: "Deadline",
			width: 150,
		},
		{
			field: "actions",
			headerName: "Actions",
			width: 220,
			sortable: false,
			filterable: false,

			renderCell: (params) => (
				<Stack
					direction="row"
					spacing={1}
				>
					<Button
						size="small"
						variant="outlined"
						onClick={() =>
							handleEdit(
								params.row
							)
						}
					>
						Edit
					</Button>

					<Button
						size="small"
						variant="contained"
						onClick={() =>
							handleStats(
								params.row.jobId
							)
						}
					>
						Stats
					</Button>
				</Stack>
			),
		},
	];

	return (
    <AdminLayout>
      <Stack
        direction="row"
        justifycontent="space-between"
        alignitems="center"
        sx={{ mb: 3 }}
      >
        <Typography variant="h4">Jobs</Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleCreate}
          sx={{ ml: 8 }}
        >
          Add Job
        </Button>
      </Stack>

      <Card>
        <CardContent>
          
          <DataTable rows={rows} columns={columns} />
        </CardContent>
      </Card>

      <JobDialog
        open={open}
        onClose={() => setOpen(false)}
        job={job}
        setJob={setJob}
        companies={companies}
        onSave={handleSave}
        isEdit={isEdit}
      />

      <JobStatsDialog
        open={statsOpen}
        onClose={() => setStatsOpen(false)}
        stats={stats}
      />
    </AdminLayout>
  );
}

export default Jobs;