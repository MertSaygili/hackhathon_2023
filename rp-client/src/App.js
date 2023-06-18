import React, { useEffect, useState, useId } from 'react';
import { socket } from './socket';

const short = require('short-uuid');

let flag = 0

function App() {

  const id = useId()

  let [playlistStorage, setPlaylistStorage] = useState([]);
  const [showed, setShowed] = useState(null);

  useEffect(() => {
    socket.on("connect", () => {
      // rp login
      console.log("connected");
      const name = localStorage.getItem("name") || short.generate();
      var playlist = localStorage.getItem("playlist")
      playlist !== null ? playlist = JSON.parse(playlist) : playlist = []
      setPlaylistStorage(playlist)
      socket.emit("rp-login", { name: name, mac: window.location.pathname.replace("/", ""), playlist: playlist.map(item=>{
        return {
          id: item.id,
          name: item.name,
          type: item.type,
          // file: item.file,
          play_time: item.play_time
        }
      }) })
    })

    socket.on("disconnect", () => {
      console.log("disconnected");
    })

    return () => {
      socket.off("connect")
      socket.off("disconnect")
    }
  }, []);

  useEffect(() => {
    // rp change name
    socket.on("rp-change-name", name => {
      console.log(name);
      localStorage.setItem("name", name)
    })

    return () => {
      socket.off("rp-change-name")
    }
  }, []);

  useEffect(() => {
    // rp add playlist
    socket.on("rp-add-playlist", item => {
      var playlist = localStorage.getItem("playlist")
      playlist !== null ? playlist = JSON.parse(playlist) : playlist = []
      playlist.push(item)
      localStorage.setItem("playlist", JSON.stringify(playlist))
      setPlaylistStorage(playlist)
    })

    return () => {
      socket.off("rp-add-playlist")
    }
  }, []);

  useEffect(() => {
    // rp delete playlist
    socket.on("rp-delete-playlist", id => {
      var playlist = localStorage.getItem("playlist")
      playlist !== null ? playlist = JSON.parse(playlist) : playlist = []
      playlist = playlist.filter((value) => value.id !== id)
      localStorage.setItem("playlist", JSON.stringify(playlist))
      setPlaylistStorage(playlist)
    })

    return () => {
      socket.off("rp-delete-playlist")
    }
  }, []);

  useEffect(() => {
    // rp update playlist
    socket.on("rp-update-playlist", item => {
      var playlist = localStorage.getItem("playlist")
      playlist !== null ? playlist = JSON.parse(playlist) : playlist = []
      playlist = playlist.map((value) => value.id !== item.id ? value : {
        id: item.id,
        name: value.name,
        type: value.type,
        file: value.file,
        play_time: item.play_time
      })
      localStorage.setItem("playlist", JSON.stringify(playlist))
      setPlaylistStorage(playlist)
      console.log("playlistUpdated ", item);
    })

    return () => {
      socket.off("rp-update-playlist")
    }
  }, []);

  useEffect(() => {
    // rp play
    socket.on("rp-play", id => {
      flag++
      var playlist = localStorage.getItem("playlist")
      playlist !== null ? playlist = JSON.parse(playlist) : playlist = []
      playPlayList(flag,id,playlist)
    })

    return () => {
      socket.off("rp-play")
    }
  }, []);


  const sleeper = (time)=>new Promise((resolve, reject)=>{
    setTimeout(() => {
      resolve(true)
    }, time);
  })


  const nextItem = async (index) => {
    if (index > playlistStorage.length - 1) {
      index = 0
    }

    await sleeper(playlistStorage[index].play_time * 1000)
    setShowed(playlistStorage[index])
    nextItem(index + 1)
  }

  
  const playPlayList = async (localFlag,id,playlist)=>{
    console.log("id", id);
    const elementIndex = playlistStorage.findIndex(item=>item.id === id)
    console.log("elementIndex",elementIndex);
    if(id){
      playlistStorage = playlist
      console.log("playlistStorage",playlistStorage);
    }
    let playingIndex = elementIndex != -1 ? elementIndex : 0
    while(localFlag === flag){
      setShowed(playlistStorage[playingIndex])
      socket.emit("rp-stream", playlistStorage[playingIndex])
      await sleeper(playlistStorage[playingIndex].play_time * 1000)
      playingIndex++
      if(playingIndex > playlistStorage.length - 1){
        playingIndex = 0
      }
    }
  }

  useEffect(() => {
    // rp show
    if (playlistStorage.length > 0) {
      flag++
      playPlayList(flag)
    }

  }, [playlistStorage]);


  return (
    <div className="App">
      {(showed && showed.type === "image") && (
        <img src={showed.file} width="192" height="384" />
      )}
      {(showed && showed.type === "video") && (
        <video width="192" height="384" autoPlay>
          <source src={showed.file} type="video/mp4" />
        </video>
      )}

    </div>
  );
}

export default App;
