import 'dart:io';

import 'package:flutter/material.dart';
import 'package:hackathon_2023_inifia/feature/util/controllers/image_picker_controller.dart';
import 'package:image_picker/image_picker.dart';
import 'package:video_player/video_player.dart';

class DummyImagePickerScreen extends StatefulWidget {
  const DummyImagePickerScreen({super.key});

  @override
  State<DummyImagePickerScreen> createState() => _DummyImagePickerScreenState();
}

class _DummyImagePickerScreenState extends State<DummyImagePickerScreen> {
  final String _title = "Title";
  final ImagePicker picker = ImagePicker();
  VideoPlayerController? _controller;
  XFile? _image;
  XFile? _video;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(_title)),
      body: _showVideo(),
      floatingActionButton: _pickImageButton(),
    );
  }

  FloatingActionButton _pickImageButton() {
    return FloatingActionButton(
      onPressed: () async {
        // await getImage();
        await getVideo();
      },
      child: const Icon(Icons.camera),
    );
  }

  Widget _showImage() {
    return _image == null ? const Center(child: Text("No image selected")) : Image.file(File(_image!.path));
  }

  Widget _showVideo() {
    return _video == null
        ? const Center(child: Text("No video selected"))
        : _controller == null
            ? const Center(child: CircularProgressIndicator())
            : AspectRatio(
                aspectRatio: _controller!.value.aspectRatio,
                child: VideoPlayer(_controller!),
              );
  }

  Future<void> getVideo() async {
    ImagePickerController().pickVideo().then((XFile video) {
      _video = video;
      _controller = VideoPlayerController.file(File(_video!.path))
        ..initialize().then((_) {
          setState(() {});
          _controller!.play();
        });
    });
    setState(() {});
  }
}
