import 'dart:io';

import 'package:image_picker/image_picker.dart';
import 'package:video_player/video_player.dart';

class VideoController {
  VideoPlayerController getVideoController(XFile video) {
    VideoPlayerController? _videoPLayerController;
    _videoPLayerController = VideoPlayerController.file(File(video.path))
      ..initialize().then((_) {
        _videoPLayerController!.play();
      });

    return _videoPLayerController;
  }
}
