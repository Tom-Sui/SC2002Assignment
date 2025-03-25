<h1>How to use cmd to update/download code</h1>
To download git:

```
https://git-scm.com/downloads
```
First time using git may require login, just follow cmd prompt instructions

<h2>Download Code</h2>

<h3>If first time download the code</h3>

```
git clone "replace with git http URL"
```

```
cd SC2002Assignment
```

<h3>If update already downloaded code</h3>
Make sure update code before you make any changes
Changes will be overwrite if commit "git pull"

```
cd your SC2002Assignment root folder
```

```
git pull
```

<h2>Switch to existing or new branch</h2>

New branch:
```
git checkout -b Branch Name
```

Existing branch:
```
git checkout Branch Name
```
* Name branch as (example): Update/Function

<h2>Update your changes in local</h2>

```
git add .
```

<h2>Add comment</h2>

Add comment before upload so people could know what has been modified
```
git commit -m "Your Comment"
```

<h2>Upload your code to github</h2>
Do note that do not direct push your work to the main branch, the main branch is leave for finalized parts.
Update your new changes to a different branch and merge that branch to main if finalized

```
git push
```

* Make sure select correct branch, you will be able to see the changes in the github page
