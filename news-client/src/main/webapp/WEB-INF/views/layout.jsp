<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	 <table style="width:100%; height:100%; border:3px solid black; border-collapse: collapse;">
      <tr>
        <td style="border:3px solid black">
          <tiles:insertAttribute name="header" />
        </td>
      </tr>
      <tr>
        <td style="border:3px solid black">
          <tiles:insertAttribute name="body" />
        </td>
      </tr>
      <tr>
        <td style="border:3px solid black">
          <tiles:insertAttribute name="footer" />
        </td>
      </tr>
    </table>
</body>
</html>