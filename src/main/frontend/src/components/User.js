
import Appbar from './Appbar'
import FileTable from './FileTable'
import Upload from './Upload'
import React, { useState } from 'react';
import axios from 'axios';



export default function User() {
  const [fileList, setFileList] = useState([]);

  const fetchFileList = async () => {
    let url=""
    if(window!=undefined)
      url=window.location.href;
    const userId =url.substring(url.lastIndexOf('/') + 1);
    // console.log("hey");
    // console.log(url);
    // console.log(userId);
    // if("hey"==="hey"+userId)
    // console.log("http://localhost:8080/tempStore/listFiles"+userId);
    if (userId === "") {
  }
  else
  {
    axios.get("http://localhost:8080/tempStore/listFiles/"+userId).then(
      (res) => {
        // console.log(res);
        setFileList(res.data);
      }

    )
    console.log("[id] inside fetchfilelist");
    console.log(fileList);
  }
    
  }
  return (
    <div className="App">
      <Appbar />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <FileTable fileList={fileList} setFileList={setFileList} fetchFileList={fetchFileList}/>
      <Upload buttonFlag={true} fileList={fileList} setFileList={setFileList} fetchFileList={fetchFileList}/>
    </div>
  )
}
