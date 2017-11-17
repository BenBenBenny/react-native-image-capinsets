import React, { Component } from 'react';
import {
  StyleSheet,
  Image,
  ImageBackground
} from 'react-native';

const styles = StyleSheet.create({

});

class ImageCapInset extends Component {
  render() {
    const ImageComponent = this.props.children ? ImageBackground : Image

    return (
      <ImageComponent
        {...this.props}
        resizeMode={Image.resizeMode.stretch}
      />
    );
  }
}

export default ImageCapInset;
