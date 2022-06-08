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






export default function FileTable() {

  const [fileList, setFileList] = useState([]);
  const { asPath } = useRouter();

  const handleDelete = (fileName, e) => {
    axios.get("http://localhost:8080/tempStore/delete", { params: { fileName: fileName } });
    let filteredArray = fileList.filter(item => item.fileName !== fileName);
    // console.log(filteredArray);
    setFileList(filteredArray);
    // window.location.reload();
  }

  const handleClearAll = (fileName, e) => {
    const res = axios.get("http://localhost:8080/tempStore/deleteAll");
    setFileList([]);
    // window.location.reload();
  }

  const handleDownload = async (fileName, e) => {
    const res = await axios.get("http://localhost:8080/tempStore/download/", { params: { fileName: fileName } })
    console.log(res);
  }

  

  const fetchFileList = () => {
    let url=""
    if(window!=undefined)
      url=window.location.href;
    const userId =url.substring(url.lastIndexOf('/') + 1);
    console.log("hey");
    console.log(url);
    console.log(userId);
    // if("hey"==="hey"+userId)
    // console.log("http://localhost:8080/tempStore/listFiles"+userId);
    if (userId === "") {
    //   axios.get("http://localhost:8080/tempStore/listFiles").then(
    //   (res) => {
    //     console.log("going in");
    //     setFileList(res.data);
    //   }

    // )
  }
  else
  {
    axios.get("http://localhost:8080/tempStore/listFiles/"+userId).then(
      (res) => {
        // console.log(res);
        setFileList(res.data);
      }

    )
  }
    
  }

  useEffect(() => {
    // console.log(window.location.href)
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
            {fileList.map((file) => (

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
                    {file.fileName}
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
      <Button variant="contained" style={{
        backgroundColor: "red",

      }}
        onClick={handleClearAll}
      >Clear All</Button>
    </Box>
  );
}