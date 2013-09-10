<html>

<head>
	<meta name='layout' content='main'/>
	<title>User Cache</title>
	<parameter name="name" value=" USER CACHE " />
</head>

<body>

<g:if test='${cacheUser}'>

<table>
	<tr>
		<td>Size</td>
		<td>$ { cacheUser?.size}</td>
	</tr>
	<tr>
		<td>Status</td>
		<td>${cacheUser?.status}</td>
	</tr>
	<tr>
		<td>Name</td>
		<td>${cacheUser?.name}</td>
	</tr>
	<tr>
		<td>GUID</td>
		<td>${cacheUser?.guid}</td>
	</tr>
	<tr>
		<td>Statistics</td>
		<td>
			<table>
				<tr>
					<td>cacheUser Hits</td>
					<td>${cacheUser?.statistics?.cacheUserHits}</td>
				</tr>
				<tr>
					<td>In-memory Hits</td>
					<td>${cacheUser?.statistics?.inMemoryHits}</td>
				</tr>
				<tr>
					<td>On-disk Hits</td>
					<td>${cacheUser?.statistics?.onDiskHits}</td>
				</tr>
				<tr>
					<td>cacheUser Misses</td>
					<td>${cacheUser?.statistics?.cacheUserMisses}</td>
				</tr>
				<tr>
					<td>Object Count</td>
					<td>${cacheUser?.statistics?.objectCount}</td>
				</tr>
				<tr>
					<td>Memory Store Object Count</td>
					<td>${cacheUser?.statistics?.memoryStoreObjectCount}</td>
				</tr>
				<tr>
					<td>Disk Store Object Count</td>
					<td>${cacheUser?.statistics?.diskStoreObjectCount}</td>
				</tr>
				<tr>
					<td>Statistics Accuracy Description</td>
					<td>${cacheUser?.statistics?.statisticsAccuracyDescription}</td>
				</tr>
				<tr>
					<td>Average Get Time</td>
					<td>${cacheUser?.statistics?.averageGetTime}</td>
				</tr>
				<tr>
					<td>Eviction Count</td>
					<td>${cacheUser?.statistics?.evictionCount}</td>
				</tr>
			</table>
		</td>
	</tr>

	<tr><th colspan='2'>${cacheUser?.size} user${cacheUser?.size == 1 ? '' : 's'}</th></tr>
	<tr>
		<th>Username</th>
		<th>User</th>
	</tr>
	<g:each var='k' in='${cacheUser?.keys}'>
	<tr>
		<td>${k}</td>
		<td>${cacheUser.get(k)?.value}</td>
	</tr>
	</g:each>
</table>
</g:if>
<g:else>
<h4>Not Caching Users</h4>
</g:else>

</body>
</html>
