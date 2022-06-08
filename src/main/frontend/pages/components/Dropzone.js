import React, { useState } from 'react';
import axios from 'axios';
import { useDropzone } from 'react-dropzone'
import Link from '@mui/material/Link';
import Box from '@mui/material/Box';
import SummarizeOutlinedIcon from '@mui/icons-material/SummarizeOutlined';
import Button from '@mui/material/Button';
import { useRouter } from 'next/router'




const Dropzone = (props) => {


  let userId = "";

  // const asPath = useRouter();
  const router = useRouter();


  const { acceptedFiles, getRootProps, getInputProps } = useDropzone({ multiple: true });


  const files = acceptedFiles.map(file => (
    <li key={file.path}>
      {file.path} - {file.size} bytes
    </li>
  ));


  const sendFiles = (uploadFile) => {
    console.log("user");
    if(userId==="")
      userId = router.asPath.substring(router.asPath.lastIndexOf('/') + 1);
    
    console.log(userId);
    const formData = new FormData();

    formData.append("file", uploadFile);
    // formData.append("uploadFileInfo", JSON.stringify(uploadFileInfo));

    axios.post("http://localhost:8080/tempStore/upload/" + userId, formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    }).then(
      () => {
        console.log("file uploaded successfully");
      }
    ).catch(err => {
      console.log(err);
    });

  }



  const handleSubmit = async (e) => {
    e.preventDefault()
    const res = await axios.get("http://localhost:8080/tempStore/createUser");
    // setUserId(res.data)
    userId = res.data
    console.log("hey")
    console.log(res.data)


    acceptedFiles.map(sendFiles)

    router.push('/User/' + userId);
    // window.location.reload();
  }

  const handleAddFiles = (e) => {
    e.preventDefault()
    console.log("INSIDE ADD")

    acceptedFiles.map(sendFiles)
    console.log("INSIDE ADD OUTSIDE MAP")
    // window.location.reload();
    if(userId==="")
      userId = router.asPath.substring(router.asPath.lastIndexOf('/') + 1);
    // router.push('/User/' + userId);
    window.location.reload();
  }

  const checkURL = () => {

    // console.log(props.buttonFlag)
    if (props.buttonFlag) {

      return <Button variant="contained" onClick={handleAddFiles}>Add files</Button>
    }

    return <Button variant="contained" onClick={handleSubmit}>Submit</Button>
  }



  return (
    <div>
      <Box sx={{ display: 'flex', borderRadius: '16px', justifyContent: 'center', border: 1, }} >
        <section className="container">
          <div {...getRootProps({ className: 'dropzone' })}>
            <input {...getInputProps()} />
            <Box sx={{ display: 'flex', justifyContent: 'center' }} >
              <SummarizeOutlinedIcon fontSize="large" />
            </Box>
            <h2>Drag 'n' drop some files here, or click to select files</h2>
          </div>
          <aside>
            <h4>Files</h4>
            <ul>{files}</ul>
          </aside>
        </section>
      </Box>
      <br />
      {checkURL()}
    </div>
  )
};

export default Dropzone;


