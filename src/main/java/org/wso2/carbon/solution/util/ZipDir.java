/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.solution.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.solution.CarbonSolutionException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This Java program demonstrates how to compress a directory in ZIP format.
 *
 * @author www.codejava.net
 */
public class ZipDir extends SimpleFileVisitor<Path> {
    private static Log log = LogFactory.getLog(ZipDir.class);
    private static ZipOutputStream zos;

    private Path sourceDir;

    public ZipDir(Path sourceDir) {
        this.sourceDir = sourceDir;
    }

    public static void zip(String dirPath, String extension) throws CarbonSolutionException {
        Path sourceDir = Paths.get(dirPath);

        try {
            String zipFileName = dirPath.concat("." + extension);
            zos = new ZipOutputStream(new FileOutputStream(zipFileName));

            Files.walkFileTree(sourceDir, new ZipDir(sourceDir));

            zos.close();
        } catch (IOException ex) {
            throw new CarbonSolutionException("Error occurred while zip.", ex);
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {

        try {
            Path targetFile = sourceDir.relativize(file);

            zos.putNextEntry(new ZipEntry(targetFile.toString()));

            byte[] bytes = Files.readAllBytes(file);
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
        } catch (IOException ex) {
            log.warn(ex.getMessage());
        }

        return FileVisitResult.CONTINUE;
    }
}