/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.engine.impl.cmd;

import java.io.Serializable;

import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.runtime.Execution;

/**
 * @author Frederik Heremans
 */
public class HasExecutionVariableCmd implements Command<Boolean>, Serializable {

    private static final long serialVersionUID = 1L;
    protected String executionId;
    protected String variableName;
    protected boolean isLocal;

    public HasExecutionVariableCmd(String executionId, String variableName, boolean isLocal) {
        this.executionId = executionId;
        this.variableName = variableName;
        this.isLocal = isLocal;
    }

    @Override
    public Boolean execute(CommandContext commandContext) {
        if (executionId == null) {
            throw new FlowableIllegalArgumentException("executionId is null");
        }
        if (variableName == null) {
            throw new FlowableIllegalArgumentException("variableName is null");
        }

        ExecutionEntity execution = CommandContextUtil.getExecutionEntityManager(commandContext).findById(executionId);

        if (execution == null) {
            throw new FlowableObjectNotFoundException("execution " + executionId + " doesn't exist", Execution.class);
        }

        boolean hasVariable = false;

        if (isLocal) {
            hasVariable = execution.hasVariableLocal(variableName);
        } else {
            hasVariable = execution.hasVariable(variableName);
        }

        return hasVariable;
    }
}
