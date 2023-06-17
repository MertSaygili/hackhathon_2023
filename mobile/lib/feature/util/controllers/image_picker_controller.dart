import 'package:image_picker/image_picker.dart';

class ImagePickerController {
  late final ImagePicker _picker;

  ImagePickerController() {
    _picker = ImagePicker();
  }

  Future<XFile> pickImage() async {
    final XFile? image = await _picker.pickImage(source: ImageSource.gallery);
    if (image != null) {
      return image;
    } else {
      throw Exception("No image selected");
    }
  }

  Future<XFile> pickVideo() async {
    final XFile? video = await _picker.pickVideo(source: ImageSource.gallery);
    if (video != null) {
      return video;
    } else {
      throw Exception("No video selected");
    }
  }
}
