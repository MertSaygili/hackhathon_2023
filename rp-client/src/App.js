import React, { useEffect, useId } from 'react';
import { socket } from './socket';

const short = require('short-uuid');

function App() {

  const id = useId()

  useEffect(() => {
    // rp login
    const name = localStorage.getItem("name") || short.generate();
    socket.emit("rp-login", { name: name, mac:window.location.pathname.replace("/", ""), playlist: [] })
  }, []);

  useEffect(() => {
    // rp change name
    socket.on("rp-change-name", name=>{
      console.log(name)
      localStorage.setItem("name", name)
    })

    return () => {
      socket.off("rp-change-name")
    }
  }, []);

  useEffect(() => {
    // rp add playlist
    socket.on("rp-add-playlist", name=>{
      console.log(name)
    })

    return () => {
      socket.off("rp-add-playlist")
    }
  }, []);

  useEffect(() => {
    // rp delete playlist
    socket.on("rp-delete-playlist", name=>{
      console.log(name)
    })

    return () => {
      socket.off("rp-delete-playlist")
    }
  }, []);

  useEffect(() => {
    // rp update playlist
    socket.on("rp-update-playlist", name=>{
      console.log(name)
    })

    return () => {
      socket.off("rp-update-playlist")
    }
  }, []);

  return (
    <div className="App">

    </div>
  );
}

export default App;
