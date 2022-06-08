import React, { useState, useEffect } from 'react';
import Dropzone from './Dropzone';
import axios from 'axios';

const UserProfile = () => {

    const [userProfiles, setUserProfiles] = useState([]);

    const fetchUserProfiles = () => {
        axios.get("http://localhost:8080/api/v1/user-profile").then(
            (res) => {
                console.log(res);
                setUserProfiles(res.data);
            }


        )
    }

    useEffect(() => {
        fetchUserProfiles();
    }, []);

    return userProfiles.map((
        userProfile, index
    ) => {
        return <div key={index}>
            <br />
            <br />

            <h1>{userProfile.userName}</h1>
            <p>{userProfile.userProfileId}</p>
            <Dropzone {...userProfile} />
            <br />
        </div>
    }
    )
};

export default UserProfile;