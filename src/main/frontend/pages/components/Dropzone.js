import React, { useState, useCallback } from 'react';
import axios from 'axios';
import { useDropzone } from 'react-dropzone'
import Link from '@mui/material/Link';
import Box from '@mui/material/Box';
import SummarizeOutlinedIcon from '@mui/icons-material/SummarizeOutlined';
import Button from '@mui/material/Button';
import { useRouter } from 'next/router'




const Dropzone = ({ buttonFlag, fileList, setFileList, fetchFileList }) => {


  let userId = "";

  // const asPath = useRouter();
  const router = useRouter();

  const handleSubmitBtn = useCallback(() => {
    console.log("inside on drop");
    setFilesSelectedFlag(true);
    setSubmitFlag(true);
  }, []);


  const { acceptedFiles, getRootProps, getInputProps } = useDropzone({ onDrop:() => handleSubmitBtn(),multiple: true });

  const [submitFlag, setSubmitFlag] = useState(false);
  const [filesSelectedFlag, setFilesSelectedFlag] = useState(false);

  // const onDrop = useCallback(acceptedFiles => {
  //   // Do something with the files
  // }, [])

  // }

  

  const delay = ms => new Promise(res => setTimeout(res, ms));


  const sendFiles = async () => {
    console.log("user");
    if (userId === "")
      userId = router.asPath.substring(router.asPath.lastIndexOf('/') + 1);

    // console.log(userId);
    for (const uploadFile of acceptedFiles) {
      const formData = new FormData();

      formData.append("file", uploadFile);
      // formData.append("uploadFileInfo", JSON.stringify(uploadFileInfo));

      await axios.post("http://localhost:8080/tempStore/upload/" + userId, formData, {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      })
      // .then(
      //   () => {
      //     console.log("file uploaded successfully");
      //   }
      // ).catch(err => {
      //   console.log(err);
      // });
      console.log("file uploaded successfully");
      
    }
    setSubmitFlag(false);
    setFilesSelectedFlag(false);

  }



  const handleSubmit = async (e) => {
    e.preventDefault()
    const res = await axios.get("http://localhost:8080/tempStore/createUser");
    // setUserId(res.data)
    userId = res.data
    // console.log("hey")
    // console.log(res.data)


    // acceptedFiles.map(sendFiles)
    await sendFiles();

    router.push('/User/' + userId);

  }

  const handleAddFiles = async (e) => {
    e.preventDefault()
    // console.log("INSIDE ADD")
    if (userId === "") {
      userId = router.asPath.substring(router.asPath.lastIndexOf('/') + 1);
    }

    console.log("inside dropzone")
    await sendFiles()
    // await delay(3000);
    // console.log("Waited 5s");
    // console.log("INSIDE ADD OUTSIDE MAP")
    // window.location.reload();

    // router.push('/User/' + userId);
    // window.location.reload();
    // let newFiles=[];
    // acceptedFiles.map(file => (
    // newFiles.push(file.name)
    // ));
    // console.log(typeof(fileList));
    // console.log(typeof(acceptedFiles));


    // console.log(fileList);
    // console.log(acceptedFiles);
    // let filteredArray = fileList.concat(acceptedFiles);
    // setFileList(filteredArray);
    // console.log("http://localhost:8080/tempStore/listFiles/"+userId);
    // let res = await axios.get("http://localhost:8080/tempStore/listFiles/"+userId);
    // setFileList(res.data);
    fetchFileList()


    console.log(fileList)
    // files=null;

  }

  const checkFilesSelected = () => {
    // console.log("outside preview")
    if (filesSelectedFlag) {
      // console.log("inside preview")
      const files = acceptedFiles.map(file => (
        <li key={file.path}>
          {file.path} - {file.size} bytes
        </li>
      ));

      return files;
    }


  }

  const checkURL = () => {
    // async (event)=>{await handleAddFiles(event)}
    // async (event)=>{await handleSubmit(event)}
    if(submitFlag)
      {
        if (buttonFlag) {

          return <Button variant="contained" onClick={handleAddFiles}>Add files</Button>
        }
        else
        {
          return <Button variant="contained" onClick={handleSubmit}>Submit</Button>
        }
      }

    // console.log(props.buttonFlag)
    

    
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
            <h2>Drag and drop some files here, or click to select files</h2>
          </div>
          <aside>
            <h4>Selected Files:</h4>
            <ul>{checkFilesSelected()}</ul>
          </aside>
        </section>
      </Box>
      <br />
      {checkURL()}
    </div>
  )
};

export default Dropzone;


