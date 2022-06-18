
import Appbar from './Appbar'
import FileTable from './FileTable'
import Upload from './Upload'
import React, { useState } from 'react';
import axios from 'axios';

function Home() {
  const [fileList, setFileList] = useState([]);

  const fetchFileList = () => {
    let url = ""
    if (window != undefined)
      url = window.location.href;
    const userId = url.substring(url.lastIndexOf('/') + 1);
    // console.log("hey");
    // console.log(url);
    // console.log(userId);
    // if("hey"==="hey"+userId)
    // console.log("http://localhost:8080/tempStore/listFiles"+userId);
    if (userId === "") {
      //   axios.get("http://localhost:8080/tempStore/listFiles").then(
      //   (res) => {
      // console.log("going in");
      //     setFileList(res.data);
      //   }

      // )
    }
    else {
      axios.get("http://localhost:8080/tempStore/listFiles/" + userId).then(
        (res) => {
          // console.log(res);
          setFileList(res.data);
        }

      )
    }

  }
  return (
    <div className="App">
      <div>Hey</div>
      <Appbar />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <FileTable fileList={fileList} setFileList={setFileList} fetchFileList={fetchFileList} />
      <Upload buttonFlag={false} fileList={fileList} setFileList={setFileList} />
    </div>
  );
}

export default Home;
