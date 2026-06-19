import { DataGrid } from "@mui/x-data-grid";
import { Paper } from "@mui/material";

function DataTable({ rows, columns, loading = false }) {
	return (
    <Paper sx={{ height: 600 }}>
      <DataGrid
        rows={rows}
        columns={columns}
        loading={loading}
        disableRowSelectionOnClick
        pageSizeOptions={[5, 10, 25, 50]}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: 10,
            },
          },
        }}
        sx={{
          border: 0,

          "& .MuiDataGrid-columnHeaders": {
            backgroundColor: "#f5f5f5",
            fontSize: "15px",
            fontWeight: "bold",
          },

          "& .MuiDataGrid-row:hover": {
            backgroundColor: "#f9f9f9",
          },

          "& .MuiDataGrid-cell": {
            borderBottom: "1px solid #eee",
          },

          "& .MuiDataGrid-footerContainer": {
            borderTop: "1px solid #ddd",
          },
        }}
      />
    </Paper>
  );
}

export default DataTable;
