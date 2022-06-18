import React from 'react';
import Box from '@mui/material/Box';
import Dropzone from './Dropzone'
import SummarizeOutlinedIcon from '@mui/icons-material/SummarizeOutlined';

const Upload = ({ buttonFlag ,fileList, setFileList,fetchFileList}) => {


    return (
        <div>
            <br />
            <br />
            


            <Dropzone buttonFlag={buttonFlag} fileList={fileList} setFileList={setFileList} fetchFileList={fetchFileList}/>
            <br />
            <br />
            <br />
            <br />
            <br />
            <br />
            <br />
            <br />
            <br />
            <br />
            <br />
            <br />

        </div>
    );
};

export default Upload;