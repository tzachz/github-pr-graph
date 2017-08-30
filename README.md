# github-pr-graph
Generates a DOT file graphing open PRs and their relationships - especially useful when one PR is based on another PR's base branch. Output example:

![image](https://user-images.githubusercontent.com/2573953/29886875-941192c2-8d89-11e7-8555-43a7c7edcd88.png)

# Usage
 1. Make sure you have Java8 installed
 2. Have your GitHub user+password ready, or create and save an [access token](https://github.com/settings/tokens) (recommended!)
 3. Download and extract the packaged [github-pr-graph.zip](https://github.com/tzachz/github-pr-graph/raw/master/github-pr-graph.zip), 
 4. Call the `github-pr-grap` script under `bin` in the extracted folder:

```bash
# Usage:
# To use with a token granting access to REPO under ORG: 
./github-pr-graph <ORG> <REPO> -token <TOKEN> 
# OR, to use with user+password granting access to REPO under ORG (won't work for users with 2FA enabled): 
./github-pr-graph <ORG> <REPO> -u <USER_NAME> -p <PASSWORD> 

# To filter PRs by keyword, add -k and the keyword to search for:
./github-pr-graph <ORG> <REPO> -token <TOKEN> -k api
```

The result would be a file named `<ORG>-<REPO>-PR-graph.dot` which can be viewed using any DOT file viewer (for Ubuntu, try [xdot](https://pypi.python.org/pypi/xdot))
