#!/usr/bin/env bash

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

VERSION=4.4.0.2170-linux
echo "Downloading sonar-scanner....."
rm -rfv /tmp/sonar-scanner-cli-$VERSION.zip
wget -P /tmp/ -q https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-$VERSION.zip
echo "Download completed."

echo "Unziping downloaded file..."
unzip /tmp/sonar-scanner-cli-$VERSION.zip -d /tmp/
echo "Unzip completed."
rm /tmp/sonar-scanner-cli-$VERSION.zip

echo "Installing"
sudo ln -s /tmp/sonar-scanner-$VERSION/bin/sonar-scanner /usr/bin/sonar-scanner
echo "Installation completed successfully."

sonar-scanner --version
echo "You can use sonar-scanner!"