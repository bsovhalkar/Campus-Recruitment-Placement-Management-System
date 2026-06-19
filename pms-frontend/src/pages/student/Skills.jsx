import EmptyState from "../../components/EmptyState";
import PageLoader from "../../components/PageLoader";
import { useEffect, useState } from "react";
import ConfirmDialog from "../../components/ConfirmDialog";

import { Typography, Button, Paper, Stack, TextField ,Card,CardContent} from "@mui/material";

import { DataGrid } from "@mui/x-data-grid";

import StudentLayout from "../../layouts/StudentLayout";

import {
  getSkills,
  addSkill,
  updateSkill,
  deleteSkill,
} from "../../api/skillApi";

function StudentSkills() {
  const [skills, setSkills] = useState([]);

  const [skillName, setSkillName] = useState("");
  const [proficiencyLevel, setProficiencyLevel] = useState("");
  const [loading, setLoading] = useState(true);

  const [addDialog, setAddDialog] = useState(false);

  const [deleteDialog, setDeleteDialog] = useState(false);
  const [selectedSkill, setSelectedSkill] = useState(null);

  const [editSkill, setEditSkill] = useState(null);

  useEffect(() => {
    loadSkills();
  }, []);

  const loadSkills = async () => {
    try {
      setLoading(true);

      const response = await getSkills();

      setSkills(response.data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleAdd = async () => {
    if (!skillName.trim() || !proficiencyLevel.trim()) {
      return;
    }

    try {
      await addSkill({
        skillName,
        proficiencyLevel,
      });

      setSkillName("");
      setProficiencyLevel("");

      loadSkills();
    } catch (error) {
      console.error(error);
    }
  };

  const handleUpdate = async () => {
    if (!editSkill) return;

    try {
      await updateSkill(editSkill.skillId, {
        skillName: editSkill.skillName,
        proficiencyLevel: editSkill.proficiencyLevel,
      });

      setEditSkill(null);

      loadSkills();
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteSkill(id);
      loadSkills();
    } catch (error) {
      console.error(error);
    }
  };

  const rows = skills.map((skill) => ({
    ...skill,
    id: skill.skillId,
  }));

  const columns = [
    {
      field: "skillName",
      headerName: "Skill",
      width: 250,
    },
    {
      field: "proficiencyLevel",
      headerName: "Proficiency",
      width: 250,
    },
    {
      field: "actions",
      headerName: "Actions",
      width: 220,

      renderCell: (params) => (
        <>
          <Button
            size="small"
            variant="outlined"
            sx={{ mr: 1 }}
            onClick={() =>
              setEditSkill({
                skillId: params.row.skillId,
                skillName: params.row.skillName,
                proficiencyLevel: params.row.proficiencyLevel,
              })
            }
          >
            Edit
          </Button>

          <Button
            color="error"
            size="small"
            variant="outlined"
            onClick={() => {
              setSelectedSkill(params.row.skillId);
              setDeleteDialog(true);
            }}
          >
            Delete
          </Button>
        </>
      ),
    },
  ];
  if (loading) {
    return (
      <StudentLayout>
        <PageLoader />
      </StudentLayout>
    );
  }
  return (
    <StudentLayout>
      <Typography variant="h4" mb={3}>
        Skills
      </Typography>

      {/* Add Skill Form */}
      <Paper sx={{ p: 3, mb: 3 }}>
        <Typography variant="h6" mb={2}>
          Add Skill
        </Typography>

        <Stack direction="row" spacing={2}>
          <TextField
            label="Skill"
            value={skillName}
            onChange={(e) => setSkillName(e.target.value)}
          />

          <TextField
            label="Proficiency"
            value={proficiencyLevel}
            onChange={(e) => setProficiencyLevel(e.target.value)}
          />

          <Button variant="contained" onClick={() => setAddDialog(true)}>
            Add Skill
          </Button>
        </Stack>
      </Paper>

      {/* Edit Skill Form */}
      {editSkill && (
        <Paper sx={{ p: 3, mb: 3 }}>
          <Typography variant="h6" mb={2}>
            Edit Skill
          </Typography>

          <Stack direction="row" spacing={2}>
            <TextField
              label="Skill"
              value={editSkill.skillName}
              onChange={(e) =>
                setEditSkill({
                  ...editSkill,
                  skillName: e.target.value,
                })
              }
            />

            <TextField
              label="Proficiency"
              value={editSkill.proficiencyLevel}
              onChange={(e) =>
                setEditSkill({
                  ...editSkill,
                  proficiencyLevel: e.target.value,
                })
              }
            />

            <Button variant="contained" color="success" onClick={handleUpdate}>
              Save
            </Button>

            <Button variant="outlined" onClick={() => setEditSkill(null)}>
              Cancel
            </Button>
          </Stack>
        </Paper>
      )}

      {/* Skills Grid */}
      <Paper
        sx={{
          height: 500,
          width: "100%",
        }}
      >
        {" "}
        {rows.length === 0 ? (
          <EmptyState message="No skills added yet." />
        ) : (
          <Card>
            <CardContent>
          <DataGrid
            rows={rows}
            columns={columns}
            pageSizeOptions={[5, 10, 25, 50]}
            initialState={{
              pagination: {
                paginationModel: {
                  pageSize: 10,
                },
              },
            }}
            disableRowSelectionOnClick
          />
          </CardContent>
        </Card>


        )}
      </Paper>

      {/* Add Confirmation */}
      <ConfirmDialog
        open={addDialog}
        title="Add Skill"
        message="Add this skill?"
        onCancel={() => setAddDialog(false)}
        onConfirm={() => {
          setAddDialog(false);
          handleAdd();
        }}
      />

      {/* Delete Confirmation */}
      <ConfirmDialog
        open={deleteDialog}
        title="Delete Skill"
        message="Are you sure you want to delete this skill?"
        onCancel={() => setDeleteDialog(false)}
        onConfirm={() => {
          setDeleteDialog(false);
          handleDelete(selectedSkill);
        }}
      />
    </StudentLayout>
  );
}

export default StudentSkills;
