import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import ArticleIcon from '@mui/icons-material/Article';
import FileDownloadOutlinedIcon from '@mui/icons-material/FileDownloadOutlined';
import DeleteIcon from '@mui/icons-material/Delete';
import { IconButton, Box, Button } from '@mui/material';
import { useRouter } from 'next/router'


const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));






export default function FileTable({ fileList, setFileList, fetchFileList }) {


  const { asPath } = useRouter();

  const handleDelete = (fileName) => {
    axios.get("http://localhost:8080/tempStore/delete", { params: { fileName: fileName } });
    let filteredArray = fileList.filter(item => item.fileName !== fileName);
    // console.log(filteredArray);
    setFileList(filteredArray);
    // window.location.reload();
  }

  const handleClearAll = () => {
    axios.get("http://localhost:8080/tempStore/deleteAll");
    setFileList([]);
    // window.location.reload();
  }

  const handleDownload = async (fileName) => {
    const res = await axios.get("http://localhost:8080/tempStore/download/", { params: { fileName: fileName } })
    console.log(res);
  }


  const checkFileTable = () => {

    if (fileList.length > 0) {

      return <Button variant="contained" style={{
        backgroundColor: "red",

      }}
        onClick={handleClearAll}
      >Clear All</Button>
    }
  }





  useEffect(() => {
    console.log("calling from fileTable");
    fetchFileList();
  }, []);


  return (

    <Box>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell>Name</StyledTableCell>
              <StyledTableCell align="right">File size</StyledTableCell>
              <StyledTableCell align="right">Time of Upload</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {

              fileList.map((file) => (

                <StyledTableRow key={file.fileName}>

                  <StyledTableCell component="th" scope="row">
                    <Box
                      display={'flex'}

                      alignItems={'center'}
                      flexWrap={'wrap'}
                    >
                      <Box paddingRight={1}>
                        <ArticleIcon />
                      </Box>
                      {file.fileName.substring(file.fileName.lastIndexOf('/') + 1)}
                      <IconButton onClick={(event) => { handleDownload(file.fileName, event) }}><FileDownloadOutlinedIcon /></IconButton>
                      <IconButton onClick={(event) => { handleDelete(file.fileName, event) }}><DeleteIcon /></IconButton>
                    </Box>
                  </StyledTableCell>
                  <StyledTableCell align="right">{file.fileSize}</StyledTableCell>
                  <StyledTableCell align="right">{file.uploadTime}</StyledTableCell>
                </StyledTableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer >
      <br />
      {checkFileTable()}

    </Box>
  );
}