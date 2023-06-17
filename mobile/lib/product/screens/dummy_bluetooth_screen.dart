import 'package:flutter/material.dart';
import 'package:flutter_blue/flutter_blue.dart';

class DummyBluetoothScreen extends StatefulWidget {
  const DummyBluetoothScreen({super.key});

  @override
  State<DummyBluetoothScreen> createState() => _DummyBluetoothScreenState();
}

class _DummyBluetoothScreenState extends State<DummyBluetoothScreen> {
  FlutterBlue flutterBlue = FlutterBlue.instance;

  @override
  void initState() {
    super.initState();
  }

  void scan() {
    flutterBlue.startScan(timeout: const Duration(seconds: 4));

    // Listen to scan results
    var subscription = flutterBlue.scanResults.listen((results) {
      // do something with scan results
      for (ScanResult r in results) {
        print('${r.device.name} found! rssi: ${r.rssi}');
      }
    });

    // Stop scanning
    flutterBlue.stopScan();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          scan();
        },
        child: const Text("sss"),
      ),
    );
  }
}
