import { useEffect, useState } from "react";

import { Typography, Stack, Button, MenuItem, TextField } from "@mui/material";

import AdminLayout from "../../layouts/AdminLayout";
import DataTable from "../../components/DataTable";

import { getJobs } from "../../api/jobApi";

import { generateShortlist, getShortlistByJob } from "../../api/shortlistApi";

function Shortlists() {
	const [jobs, setJobs] = useState([]);

	const [selectedJob, setSelectedJob] = useState("");

	const [shortlist, setShortlist] = useState([]);
	
	useEffect(() => {
		loadJobs();
	}, []);

	const loadJobs = async () => {
		try {
			const response = await getJobs();

			setJobs(response.data);
		} catch (error) {
			console.error(error);
		}
	};

	const handleGenerate = async () => {
		if (!selectedJob) return;

		try {
			const response = await generateShortlist(selectedJob);

			setShortlist(response.data);
		} catch (error) {
			console.error(error);
		}
	};

	const handleView = async () => {
		if (!selectedJob) return;

		try {
			const response = await getShortlistByJob(selectedJob);

			setShortlist(response.data);
		} catch (error) {
			console.error(error);
		}
	};

	const rows = shortlist.map((item) => ({
		...item,
		id: item.applicationId,
	}));

	const columns = [
		{
			field: "rank",
			headerName: "Rank",
			width: 100,
		},
		{
			field: "studentName",
			headerName: "Student",
			width: 220,
		},
		{
			field: "cgpa",
			headerName: "CGPA",
			width: 100,
		},
		{
			field: "skillsCount",
			headerName: "Skills",
			width: 100,
		},
		{
			field: "score",
			headerName: "Score",
			width: 120,
		},
		{
			field: "jobTitle",
			headerName: "Job",
			width: 220,
		},
	];

	return (
		<AdminLayout>
			<Typography variant="h4" mb={3}>
				Shortlists
			</Typography>

			<Stack direction="row" spacing={2} mb={3}>
				<TextField
					select
					label="Select Job"
					value={selectedJob}
					onChange={(e) => setSelectedJob(e.target.value)}
					sx={{
						minWidth: 300,
					}}
				>
					{jobs.map((job) => (
						<MenuItem key={job.jobId} value={job.jobId}>
							{job.title}
						</MenuItem>
					))}
				</TextField>

				<Button variant="contained" onClick={handleGenerate}>
					Generate
				</Button>

				<Button variant="outlined" onClick={handleView}>
					View Existing
				</Button>
			</Stack>

			<DataTable rows={rows} columns={columns} />
		</AdminLayout>
	);
}

export default Shortlists;
