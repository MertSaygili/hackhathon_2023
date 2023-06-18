import React, { useEffect } from "react";
import { socket } from "../socket";

export default function StreamPlayer({ rp_info }) {
  const [streamList, setStreamList] = React.useState([]);
  const [streamIndex, setStreamIndex] = React.useState(0);

  useEffect(() => {
    socket.on("rp-stream" + rp_info.id, (newItem) => {
    //   console.log(newItem);
      setStreamList((prev) => {
        if (prev.find((item) => item.id === newItem.id)) {
          return prev.map((item, index) => {
            if (item.id === newItem.id) {
              item.play_time = newItem.play_time;
              setStreamIndex(index);
            }
            return item;
          });
        } else {
          setStreamIndex(prev.length);
          return [...prev, newItem];
        }
      });
    });
    return () => {
      socket.off("rp-stream" + rp_info.id);
    };
  }, []);

  return (
    <div>
      {streamList.length > 0 &&
        (streamList[streamIndex].type === "video" ? (
          <video autoPlay height={400}>
            <source src={streamList[streamIndex].file} type="video/mp4" />
          </video>
        ) : (
          <img
            src={streamList[streamIndex].file}
            alt="stream"
            height={400}
          />
        ))}
    </div>
  );
}
