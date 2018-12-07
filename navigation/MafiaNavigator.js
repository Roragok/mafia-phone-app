import React from "react";
import { View, Text } from "react-native";
import { createStackNavigator, createAppContainer } from "react-navigation";
import DayScreen from '../screens/DayScreen';

const AppNavigator = createStackNavigator({
  Day: DayScreen,
});

export default (AppNavigator);
